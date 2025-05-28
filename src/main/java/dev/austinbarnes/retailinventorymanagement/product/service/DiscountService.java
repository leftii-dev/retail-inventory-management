package dev.austinbarnes.retailinventorymanagement.product.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Discount;
import dev.austinbarnes.retailinventorymanagement.product.mapper.DiscountMapper;
import dev.austinbarnes.retailinventorymanagement.product.repo.DiscountRepository;
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

/**
 * Service class for managing discounts.
 * Provides methods to create, retrieve, update, and delete discounts.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountService {
    private final DiscountRepository repository;
    private final DiscountMapper mapper;

    /**
     * Creates a new discount.
     *
     * @param request the discount request DTO containing the details of the discount to create.
     * @return ResponseEntity with the created discount details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') and hasAuthority('WRITE_DISCOUNT')")
    public ResponseEntity<ApiResponseDto<DiscountResponseDTO>> createDiscount(DiscountRequestDTO request) {
        log.info("Create discount request: {}", request);
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(mapper.toEntity(request))) :
                mapper.toBasicDTO(repository.save(mapper.toEntity(request))));
    }

    /**
     * Retrieves a discount by its ID.
     *
     * @param id the ID of the discount to retrieve.
     * @return ResponseEntity with the discount details.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<DiscountResponseDTO>> getDiscountById(UUID id) {
        log.info("Get discount by ID: {}", id);
        return repository.findById(id)
                .map(discount -> ApiResponseDto.ok(isManager() ?
                        (DiscountResponseDTO) mapper.toDetailDTO(discount) :
                        mapper.toBasicDTO(discount)))
                .orElseThrow(() -> new EntityNotFoundException("Discount not found with ID: " + id));
    }

    /**
     * Retrieves all discounts.
     *
     * @return ResponseEntity with a list of all discounts.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<List<DiscountResponseDTO>>> getAllDiscounts() {
        log.info("Get all discounts");
        List<DiscountResponseDTO> discounts = repository.findAll().stream()
                .map(discount -> isManager() ?
                        (DiscountResponseDTO) mapper.toDetailDTO(discount) :
                        mapper.toBasicDTO(discount))
                .toList();
        return ApiResponseDto.ok(discounts);
    }

    /**
     * Updates an existing discount.
     *
     * @param id      the ID of the discount to update.
     * @param request the discount request DTO containing the updated details.
     * @return ResponseEntity with the updated discount details.
     */
    @Transactional
    public ResponseEntity<ApiResponseDto<DiscountResponseDTO>> updateDiscount(UUID id, DiscountRequestDTO request) {
        log.info("Update discount request: {}", request);
        Discount target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discount not found with ID: " + id));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(target)) :
                mapper.toBasicDTO(repository.save(target)));
    }

    /**
     * Deletes a discount by its ID.
     *
     * @param id the ID of the discount to delete.
     * @return ResponseEntity with no content.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') and hasAuthority('WRITE_DISCOUNT')")
    public ResponseEntity<ApiResponseDto<Void>> deleteDiscount(UUID id) {
        log.info("Delete discount with ID: {}", id);
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
