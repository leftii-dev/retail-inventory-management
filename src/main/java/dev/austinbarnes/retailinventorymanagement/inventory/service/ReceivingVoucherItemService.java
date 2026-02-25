package dev.austinbarnes.retailinventorymanagement.inventory.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemFIlterDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucherItem;
import dev.austinbarnes.retailinventorymanagement.inventory.mapper.ReceivingVoucherItemMapper;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.ReceivingVoucherItemRepository;
import dev.austinbarnes.retailinventorymanagement.inventory.specification.ReceivingVoucherItemSpecifications;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReceivingVoucherItemService {
    private final ReceivingVoucherItemRepository repository;
    private final ReceivingVoucherItemMapper mapper;

    /**
     * Creates a new receiving voucher item.
     *
     * @param request the receiving voucher item request DTO containing the details of the item to create.
     * @return ResponseEntity with the created receiving voucher item details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_RV')")
    public ResponseEntity<ApiResponseDto<ReceivingVoucherItemResponseDTO>> createReceivingVoucherItem(@Valid ReceivingVoucherItemRequestDTO request) {
        log.info("Creating receiving voucher item: {}", request);
        ReceivingVoucherItem entity = mapper.toEntity(request);
        entity.setCostLineTotal(calculateLineTotal(entity.getCostUnit(), entity.getQuantity()));
        ReceivingVoucherItem saved = repository.save(entity);
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(saved) :
                mapper.toBasicDTO(saved)
        );
    }

    /**
     * Retrieves a receiving voucher item by its ID.
     *
     * @param id the ID of the receiving voucher item to retrieve.
     * @return ResponseEntity with the receiving voucher item details.
     */
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('READ_RV')")
    public ResponseEntity<ApiResponseDto<ReceivingVoucherItemResponseDTO>> getReceivingVoucherItemById(UUID id) {
        log.info("Retrieving receiving voucher item with ID: {}", id);
        return repository.findById(id)
                .map(item -> ApiResponseDto.ok(isManager() ?
                        (ReceivingVoucherItemResponseDTO) mapper.toDetailDTO(item) :
                        mapper.toBasicDTO(item)))
                .orElseThrow(() -> new EntityNotFoundException("Receiving Voucher Item not found with ID: " + id));
    }

    /**
     * Retrieves all receiving voucher items.
     *
     * @return ResponseEntity with a list of all receiving voucher items.
     */
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('READ_RV')")
    public ResponseEntity<ApiResponseDto<List<ReceivingVoucherItemResponseDTO>>> getAllReceivingVoucherItems(
            ReceivingVoucherItemFIlterDTO filterDTO, Pageable pageable
    ) {
        log.info("Retrieving all receiving voucher items");
        Specification<ReceivingVoucherItem> spec = ReceivingVoucherItemSpecifications.applyFilters(filterDTO);
        return ApiResponseDto.ok(
                repository.findAll(spec, pageable).stream()
                        .map(item -> isManager() ?
                                (ReceivingVoucherItemResponseDTO) mapper.toDetailDTO(item) : mapper.toBasicDTO(item))
                        .toList()
        );
    }

    /**
     * Retrieves all receiving voucher items by parent receiving voucher ID.
     *
     * @param receivingVoucherId the UUID of the parent receiving voucher.
     * @return ResponseEntity with a list of receiving voucher items.
     */
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('READ_RV')")
    public ResponseEntity<ApiResponseDto<List<ReceivingVoucherItemResponseDTO>>> getReceivingVoucherItemsByReceivingVoucherId(
            UUID receivingVoucherId
    ) {
        log.info("Retrieving receiving voucher items by receiving voucher ID: {}", receivingVoucherId);
        List<ReceivingVoucherItemResponseDTO> items = repository.findAllByReceivingVoucher_IdAndActiveTrue(receivingVoucherId).stream()
                .map(item -> isManager() ?
                        (ReceivingVoucherItemResponseDTO) mapper.toDetailDTO(item) :
                        mapper.toBasicDTO(item))
                .toList();
        return ApiResponseDto.ok(items);
    }

    /**
     * Updates an existing receiving voucher item.
     *
     * @param id      the ID of the receiving voucher item to update.
     * @param request the receiving voucher item request DTO containing the updated details.
     * @return ResponseEntity with the updated receiving voucher item details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_RV')")
    public ResponseEntity<ApiResponseDto<ReceivingVoucherItemResponseDTO>> updateReceivingVoucherItem(UUID id, @Valid ReceivingVoucherItemRequestDTO request) {
        log.info("Updating receiving voucher item with ID: {}", id);
        ReceivingVoucherItem target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Receiving Voucher Item not found with ID: " + id));
        mapper.updateEntityFromRequest(request, target);
        target.setCostLineTotal(calculateLineTotal(target.getCostUnit(), target.getQuantity()));
        ReceivingVoucherItem saved = repository.save(target);
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(saved) :
                mapper.toBasicDTO(saved)
        );
    }

    /**
     * Deletes a receiving voucher item by its ID.
     *
     * @param id the ID of the receiving voucher item to delete.
     * @return ResponseEntity with no content.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_RV')")
    public ResponseEntity<ApiResponseDto<Void>> deleteReceivingVoucherItem(UUID id) {
        log.info("Deleting receiving voucher item with ID: {}", id);
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

    private BigDecimal calculateLineTotal(BigDecimal costUnit, short quantity) {
        if (costUnit == null) {
            return null;
        }
        return costUnit.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
    }
}
