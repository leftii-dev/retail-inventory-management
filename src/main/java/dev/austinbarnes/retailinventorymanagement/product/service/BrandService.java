package dev.austinbarnes.retailinventorymanagement.product.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Brand;
import dev.austinbarnes.retailinventorymanagement.product.mapper.BrandMapper;
import dev.austinbarnes.retailinventorymanagement.product.repo.BrandRepository;
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
public class BrandService {
    private final BrandRepository repository;
    private final BrandMapper mapper;

    /**
     * Creates a new brand.
     *
     * @param request the brand request DTO containing the details of the brand to create.
     * @return ResponseEntity with the created brand details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN') and hasAuthority('WRITE_PRODUCT')")
    public ResponseEntity<ApiResponseDto<BrandResponseDTO>> createBrand(BrandRequestDTO request) {
        log.info("Creating brand: {}", request);
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(mapper.toEntity(request))) :
                mapper.toBasicDTO(repository.save(mapper.toEntity(request)))
        );
    }

    /**
     * Retrieves a brand by its ID.
     *
     * @param id the ID of the brand to retrieve.
     * @return ResponseEntity with the brand details.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<BrandResponseDTO>> getBrandById(UUID id) {
        log.info("Retrieving brand with ID: {}", id);
        return repository.findById(id)
                .map(brand -> ApiResponseDto.ok(isManager() ?
                        (BrandResponseDTO) mapper.toDetailDTO(brand) :
                        mapper.toBasicDTO(brand)))
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with ID: " + id));
    }

    /**
     * Retrieves all brands.
     *
     * @return ResponseEntity with a list of all brands.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<List<BrandResponseDTO>>> getBrands() {
        log.info("Retrieving all brands");
        return ApiResponseDto.ok(repository.findAll().stream()
                .map(brand -> isManager() ?
                        (BrandResponseDTO) mapper.toDetailDTO(brand) :
                        mapper.toBasicDTO(brand))
                .toList());
    }

    /**
     * Updates an existing brand.
     *
     * @param id      the ID of the brand to update.
     * @param request the brand request DTO containing the updated details of the brand.
     * @return ResponseEntity with the updated brand details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN') and hasAuthority('WRITE_PRODUCT')")
    public ResponseEntity<ApiResponseDto<BrandResponseDTO>> updateBrand(UUID id, BrandRequestDTO request) {
        log.info("Updating brand with ID: {}", id);
        Brand target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with ID: " + id));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(target)) :
                mapper.toBasicDTO(repository.save(target))
        );
    }

    /**
     * Deletes a brand by its ID.
     *
     * @param id the ID of the brand to delete.
     * @return ResponseEntity with no content.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN') and hasAuthority('WRITE_PRODUCT')")
    public ResponseEntity<ApiResponseDto<Void>> deleteBrand(UUID id) {
        log.info("Deleting brand with ID: {}", id);
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
