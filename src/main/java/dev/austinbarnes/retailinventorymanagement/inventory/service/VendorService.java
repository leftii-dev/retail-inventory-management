package dev.austinbarnes.retailinventorymanagement.inventory.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Vendor;
import dev.austinbarnes.retailinventorymanagement.inventory.mapper.VendorMapper;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.VendorRepository;
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
public class VendorService {
    private final VendorRepository repository;
    private final VendorMapper mapper;

    /**
     * Creates a new vendor.
     *
     * @param request the vendor request DTO containing the details of the vendor to create.
     * @return ResponseEntity with the created vendor details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_VENDOR')")
    public ResponseEntity<ApiResponseDto<VendorResponseDTO>> createVendor(VendorRequestDTO request) {
        log.info("Create vendor request: {}", request);
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(mapper.toEntity(request))):
                mapper.toBasicDTO(repository.save(mapper.toEntity(request))));
    }

    /**
     * Retrieves a vendor by its ID.
     *
     * @param id the ID of the vendor to retrieve.
     * @return ResponseEntity with the vendor details.
     */
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('READ_VENDOR')")
    public ResponseEntity<ApiResponseDto<VendorResponseDTO>> getVendorById(UUID id) {
        log.info("Get vendor by ID: {}", id);
        return repository.findById(id)
                .map(vendor -> ApiResponseDto.ok(isManager() ?
                        (VendorResponseDTO) mapper.toDetailDTO(vendor) :
                        mapper.toBasicDTO(vendor)))
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found with ID: " + id));
    }

    /**
     * Retrieves all vendors.
     *
     * @return ResponseEntity with a list of all vendors.
     */
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('READ_VENDOR')")
    public ResponseEntity<ApiResponseDto<List<VendorResponseDTO>>> getAllVendors() {
        log.info("Get all vendors");
        return ApiResponseDto.ok(repository.findAll().stream()
                .map(vendor -> isManager() ?
                        (VendorResponseDTO) mapper.toDetailDTO(vendor) : mapper.toBasicDTO(vendor))
                .toList());
    }

    /**
     * Updates an existing vendor.
     *
     * @param id the ID of the vendor to update.
     * @param request the vendor request DTO containing the updated details.
     * @return ResponseEntity with the updated vendor details.
     */

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_VENDOR')")
    public ResponseEntity<ApiResponseDto<VendorResponseDTO>> updateVendor(UUID id, VendorRequestDTO request) {
        log.info("Update vendor with ID: {}, request: {}", id, request);
        Vendor target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found with ID: " + id));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(target)) :
                mapper.toBasicDTO(repository.save(target)));
    }

    /**
     * Deletes a vendor by its ID.
     *
     * @param id the ID of the vendor to delete.
     * @return ResponseEntity indicating the result of the deletion.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_VENDOR')")
    public ResponseEntity<ApiResponseDto<Void>> deleteVendor(UUID id) {
        log.info("Delete vendor with ID: {}", id);
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
