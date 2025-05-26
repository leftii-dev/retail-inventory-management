package dev.austinbarnes.retailinventorymanagement.inventory.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferItemResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.TransferItem;
import dev.austinbarnes.retailinventorymanagement.inventory.mapper.TransferItemMapper;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.TransferItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferItemService {
    private final TransferItemRepository repository;
    private final TransferItemMapper mapper;

    /**
     * Creates a new transfer item.
     *
     * @param request the transfer item request DTO containing the details of the transfer item to create.
     * @return ResponseEntity with the created transfer item details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_TRANSFER')")
    public ResponseEntity<ApiResponseDto<TransferItemResponseDTO>> createTransferItem(TransferItemRequestDTO request) {
        log.info("Creating transfer item with request: {}", request);
        return ApiResponseDto.created(
            isManager() ?
                mapper.toDetailDTO(repository.save(mapper.toEntity(request))) :
                mapper.toBasicDTO(repository.save(mapper.toEntity(request)))
        );
    }

    /**
     * Retrieves a transfer item by its ID.
     *
     * @param id the ID of the transfer item to retrieve.
     * @return ResponseEntity with the transfer item details.
     */
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('READ_TRANSFER')")
    public ResponseEntity<ApiResponseDto<TransferItemResponseDTO>> getTransferItemById(UUID id) {
        log.info("Retrieving transfer item with ID: {}", id);
        return repository.findById(id)
                .map(transferItem -> ApiResponseDto.ok(isManager() ?
                        (TransferItemResponseDTO) mapper.toDetailDTO(transferItem) :
                        mapper.toBasicDTO(transferItem)))
                .orElseThrow(() -> new EntityNotFoundException("Transfer Item not found with ID: " + id));
    }

    /**
     * Retrieves all transfer items.
     *
     * @return ResponseEntity with a list of all transfer items.
     */
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('READ_TRANSFER')")
    public ResponseEntity<ApiResponseDto<List<TransferItemResponseDTO>>> getAllTransferItems() {
        log.info("Retrieving all transfer items");
        return ApiResponseDto.ok(repository.findAll().stream()
                .map(transferItem -> isManager() ?
                        (TransferItemResponseDTO) mapper.toDetailDTO(transferItem) :
                        mapper.toBasicDTO(transferItem))
                .toList());
    }

    /**
     * Updates an existing transfer item by its ID.
     *
     * @param id the ID of the transfer item to update.
     * @param request the transfer item request DTO containing the updated details of the transfer item.
     * @return ResponseEntity with the updated transfer item details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_TRANSFER')")
    public ResponseEntity<ApiResponseDto<TransferItemResponseDTO>> updateTransferItem(UUID id, TransferItemRequestDTO request) {
        log.info("Updating transfer item with ID: {}", id);
        TransferItem target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transfer Item not found with ID: " + id));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(
            isManager() ?
                mapper.toDetailDTO(repository.save(target)) :
                mapper.toBasicDTO(repository.save(target))
        );
    }

    /**
     * Deletes a transfer item by its ID.
     *
     * @param id the ID of the transfer item to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_TRANSFER')")
    public ResponseEntity<ApiResponseDto<Void>> deleteTransferItem(UUID id) {
        log.info("Deleting transfer item with ID: {}", id);
        repository.deleteById(id);
        return ApiResponseDto.noContent();
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
