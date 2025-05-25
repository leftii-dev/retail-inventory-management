package dev.austinbarnes.retailinventorymanagement.inventory.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.mapper.ReceivingVoucherItemMapper;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.ReceivingVoucherItemRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(mapper.toEntity(request))) :
                mapper.toBasicDTO(repository.save(mapper.toEntity(request)))
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
