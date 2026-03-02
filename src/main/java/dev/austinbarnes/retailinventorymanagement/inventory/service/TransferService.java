package dev.austinbarnes.retailinventorymanagement.inventory.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.entitycode.CodeGenerator;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferFilterDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Inventory;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Transfer;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.TransferItem;
import dev.austinbarnes.retailinventorymanagement.inventory.mapper.TransferMapper;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.InventoryRepository;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.StatusRepository;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.TransferItemRepository;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.TransferRepository;
import dev.austinbarnes.retailinventorymanagement.inventory.specification.TransferSpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferService {
    private final TransferRepository repository;
    private final TransferItemRepository transferItemRepository;
    private final TransferItemService transferItemService;
    private final TransferMapper mapper;
    private final CodeGenerator codeGenerator;
    private final InventoryRepository inventoryRepository;
    private final StatusRepository statusRepository;

    /**
     * Creates a new transfer.
     *
     * @param request the transfer request DTO containing the details of the transfer to create.
     * @return ResponseEntity with the created transfer details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_TRANSFER')")
    public ResponseEntity<ApiResponseDto<TransferResponseDTO>> createTransfer(TransferRequestDTO request){
        log.info("Creating transfer with request: {}", request);
        Transfer newTransfer = mapper.toEntity(request);
        newTransfer.setTransferCode(codeGenerator.generateTransferCode());
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(newTransfer)) :
                mapper.toBasicDTO(repository.save(newTransfer))
        );
    }

    /**
     * Retrieves a transfer by its ID.
     *
     * @param id the ID of the transfer to retrieve.
     * @return ResponseEntity with the transfer details.
     */
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('READ_TRANSFER')")
    public ResponseEntity<ApiResponseDto<TransferResponseDTO>> getTransferById(UUID id) {
        log.info("Retrieving transfer with ID: {}", id);
        return repository.findById(id)
                .map(transfer -> ApiResponseDto.ok(isManager() ?
                        (TransferResponseDTO) mapper.toDetailDTO(transfer) :
                        mapper.toBasicDTO(transfer)))
                .orElseThrow(() -> new EntityNotFoundException("Transfer not found with ID: " + id));
    }

    /**
     * Retrieves all transfers.
     *
     * @return ResponseEntity with a list of all transfers.
     */
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('READ_TRANSFER')")
    public ResponseEntity<ApiResponseDto<List<TransferResponseDTO>>> getAllTransfers(
            TransferFilterDTO filterDTO, Pageable pageable
    ) {
        log.info("Retrieving all transfers");
        Specification<Transfer> spec = TransferSpecifications.applyFilters(filterDTO);
        return ApiResponseDto.ok(repository.findAll(spec, pageable).stream()
                .map(transfer -> isManager() ?
                        (TransferResponseDTO) mapper.toDetailDTO(transfer) :
                        mapper.toBasicDTO(transfer))
                .toList());
    }

    /**
     * Updates an existing transfer.
     *
     * @param id the ID of the transfer to update.
     * @param request the transfer request DTO containing the updated details.
     * @return ResponseEntity with the updated transfer details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_TRANSFER')")
    public ResponseEntity<ApiResponseDto<TransferResponseDTO>> updateTransfer(UUID id, TransferRequestDTO request) {
        log.info("Updating transfer with ID: {} and request: {}", id, request);
        Transfer target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transfer not found with ID: " + id));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(target)) :
                mapper.toBasicDTO(repository.save(target)));
    }

    /**
     * Deletes a transfer by its ID.
     *
     * @param id the ID of the transfer to delete.
     * @return ResponseEntity indicating the result of the deletion.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_TRANSFER')")
    public ResponseEntity<ApiResponseDto<Void>> deleteTransfer(UUID id) {
        log.info("Deleting transfer with ID: {}", id);
        List<UUID> itemIds = transferItemRepository.findAllByTransfer_IdAndActiveTrue(id).stream()
                .map(TransferItem::getId)
                .toList();
        itemIds.forEach(transferItemId ->
                transferItemService.deleteTransferItem(transferItemId));
        repository.deleteById(id);
        return ApiResponseDto.noContent();
    }

    /**
     * Finalizes a transfer by decrementing inventory at the source location and
     * incrementing (or creating) inventory at the destination location.
     * All items are validated for sufficient stock before any changes are committed.
     *
     * @param id the ID of the transfer to finalize.
     * @return ResponseEntity with the updated transfer details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN') and hasAuthority('WRITE_TRANSFER')")
    public ResponseEntity<ApiResponseDto<TransferResponseDTO>> finalizeTransfer(UUID id) {
        log.info("Finalizing transfer with ID: {}", id);
        Transfer transfer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transfer not found with ID: " + id));
        if (transfer.getStatus() == null) {
            throw new IllegalStateException("Transfer cannot be finalized: status is not set");
        }
        String statusName = transfer.getStatus().getName();
        if ("COMPLETED".equals(statusName) || "CANCELLED".equals(statusName)) {
            throw new IllegalStateException(
                    "Transfer cannot be finalized: current status is " + statusName);
        }
        List<TransferItem> items = transferItemRepository.findAllByTransfer_IdAndActiveTrue(id);
        List<String> insufficientItems = new ArrayList<>();
        for (TransferItem item : items) {
            Optional<Inventory> sourceInventory = inventoryRepository
                    .findByProductIdAndLocationIdAndActiveTrue(
                            item.getProduct().getId(), transfer.getLocationFrom().getId());
            if (sourceInventory.isEmpty() || sourceInventory.get().getQuantity() < item.getQuantity()) {
                int available = sourceInventory.map(Inventory::getQuantity).orElse(0);
                insufficientItems.add(String.format("Product '%s' (id=%s): required=%d, available=%d",
                        item.getProduct().getName(), item.getProduct().getId(),
                        (int) item.getQuantity(), available));
            }
        }
        if (!insufficientItems.isEmpty()) {
            throw new IllegalArgumentException(
                    "Insufficient stock for the following items:\n" + String.join("\n", insufficientItems));
        }
        for (TransferItem item : items) {
            Inventory sourceInventory = inventoryRepository
                    .findByProductIdAndLocationIdAndActiveTrue(
                            item.getProduct().getId(), transfer.getLocationFrom().getId())
                    .orElseThrow();
            sourceInventory.setQuantity(sourceInventory.getQuantity() - item.getQuantity());
            inventoryRepository.save(sourceInventory);
            inventoryRepository
                    .findByProductIdAndLocationIdAndActiveTrue(
                            item.getProduct().getId(), transfer.getLocationTo().getId())
                    .ifPresentOrElse(
                            destInventory -> {
                                destInventory.setQuantity(destInventory.getQuantity() + item.getQuantity());
                                inventoryRepository.save(destInventory);
                            },
                            () -> {
                                Inventory newInventory = new Inventory();
                                newInventory.setProduct(item.getProduct());
                                newInventory.setLocation(transfer.getLocationTo());
                                newInventory.setQuantity(item.getQuantity());
                                inventoryRepository.save(newInventory);
                            }
                    );
        }
        transfer.setStatus(statusRepository.findByName("COMPLETED")
                .orElseThrow(() -> new EntityNotFoundException("Status 'COMPLETED' not found")));
        repository.save(transfer);
        return ApiResponseDto.ok(isManager() ?
                (TransferResponseDTO) mapper.toDetailDTO(transfer) :
                mapper.toBasicDTO(transfer));
    }

    /**
     * Determines is the current user has manager or admin role.
     *
     * @return true if the user has manager or admin role, false otherwise.
     */
    private boolean isManager() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals("ROLE_MANAGER") || authority.getAuthority().equals("ROLE_ADMIN")
        );
    }
}
