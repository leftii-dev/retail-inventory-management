package dev.austinbarnes.retailinventorymanagement.inventory.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.entitycode.CodeGenerator;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherFilterDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucher;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucherItem;
import dev.austinbarnes.retailinventorymanagement.inventory.mapper.ReceivingVoucherMapper;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.ReceivingVoucherItemRepository;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.ReceivingVoucherRepository;
import dev.austinbarnes.retailinventorymanagement.inventory.specification.ReceivingVoucherSpecifications;
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

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReceivingVoucherService {
    private final ReceivingVoucherRepository repository;
    private final ReceivingVoucherItemRepository receivingVoucherItemRepository;
    private final ReceivingVoucherItemService receivingVoucherItemService;
    private final ReceivingVoucherMapper mapper;
    private final CodeGenerator codeGenerator;

    /**
     * Creates a new receiving voucher.
     *
     * @param request the receiving voucher request DTO containing the details of the receiving voucher to create.
     * @return ResponseEntity with the created receiving voucher details.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE' and hasAuthority('WRITE_RV'))")
    public ResponseEntity<ApiResponseDto<ReceivingVoucherResponseDTO>> createReceivingVoucher(
            ReceivingVoucherRequestDTO request) {
        log.info("Creating receiving voucher: {}", request);
        ReceivingVoucher newRv = mapper.toEntity(request);
        newRv.setReceivingVoucherCode(codeGenerator.generateReceivingVoucherCode());
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(newRv)) :
                mapper.toBasicDTO(repository.save(newRv))
        );
    }

    /**
     * Retrieves a receiving voucher by its ID.
     *
     * @param id the ID of the receiving voucher to retrieve.
     * @return ResponseEntity with the receiving voucher details.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('READ_RV')")
    public ResponseEntity<ApiResponseDto<ReceivingVoucherResponseDTO>> getReceivingVoucherById(UUID id) {
        log.info("Retrieving receiving voucher with ID: {}", id);
        return repository.findById(id)
                .map(receivingVoucher -> ApiResponseDto.ok(isManager() ?
                        (ReceivingVoucherResponseDTO) mapper.toDetailDTO(receivingVoucher) :
                        mapper.toBasicDTO(receivingVoucher)))
                .orElseThrow(() -> new EntityNotFoundException("Receiving Voucher not found with ID: " + id));
    }

    /**
     * Retrieves all receiving vouchers.
     *
     * @return ResponseEntity with a list of all receiving vouchers.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('READ_RV')")
    public ResponseEntity<ApiResponseDto<List<ReceivingVoucherResponseDTO>>> getAllReceivingVouchers(
            ReceivingVoucherFilterDTO filterDTO, Pageable pageable
    ) {
        log.info("Retrieving all receiving vouchers");
        Specification<ReceivingVoucher> spec = ReceivingVoucherSpecifications.applyFilters(filterDTO);
        return ApiResponseDto.ok(repository.findAll(spec, pageable).stream()
                .map(receivingVoucher -> isManager() ?
                        (ReceivingVoucherResponseDTO) mapper.toDetailDTO(receivingVoucher) :
                        mapper.toBasicDTO(receivingVoucher))
                .toList());
    }

    /**
     * Updates an existing receiving voucher.
     *
     * @param id      the ID of the receiving voucher to update.
     * @param request the receiving voucher request DTO containing the updated details.
     * @return ResponseEntity with the updated receiving voucher details.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_RV')")
    public ResponseEntity<ApiResponseDto<ReceivingVoucherResponseDTO>> updateReceivingVoucher(UUID id, ReceivingVoucherRequestDTO request) {
        log.info("Updating receiving voucher with ID: {}", id);
        ReceivingVoucher target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Receiving Voucher not found with ID: " + id));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(target)) :
                mapper.toBasicDTO(repository.save(target)));
    }

    /**
     * Deletes a receiving voucher by its ID.
     *
     * @param id the ID of the receiving voucher to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_RV')")
    @Transactional
    public ResponseEntity<ApiResponseDto<Void>> deleteReceivingVoucher(UUID id) {
        log.info("Deleting receiving voucher with ID: {}", id);
        List<UUID> itemIds = receivingVoucherItemRepository.findAllByReceivingVoucher_IdAndActiveTrue(id).stream()
                .map(ReceivingVoucherItem::getId)
                .toList();
        itemIds.forEach(receivingVoucherItemId ->
                receivingVoucherItemService.deleteReceivingVoucherItem(receivingVoucherItemId));
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
