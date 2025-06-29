package dev.austinbarnes.retailinventorymanagement.location.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.entitycode.CodeGenerator;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationFilterDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.WarehouseLocation;
import dev.austinbarnes.retailinventorymanagement.location.mapper.WarehouseLocationMapper;
import dev.austinbarnes.retailinventorymanagement.location.repo.WarehouseLocationRepository;
import dev.austinbarnes.retailinventorymanagement.location.specification.WarehouseLocationSpecifications;
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

/**
 * Service class for managing warehouse locations.
 * Provides methods to create, retrieve, update, and delete warehouse locations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WarehouseLocationService {
    private final WarehouseLocationRepository repository;
    private final WarehouseLocationMapper mapper;
    private final CodeGenerator codeGenerator;

    /**
     * Creates a new warehouse location.
     *
     * @param request the warehouse location request DTO containing the details of the warehouse location to create.
     * @return ResponseEntity with the created warehouse location details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<WarehouseLocationResponseDTO>> createWarehouseLocation(WarehouseLocationRequestDTO request) {
        log.info("Creating new warehouse location: {}", request);
        WarehouseLocation newLocation = mapper.toEntity(request);
        newLocation.setWarehouseCode(codeGenerator.generateWarehouseLocationCode());
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(newLocation)) :
                mapper.toBasicDTO(repository.save(newLocation))
        );
    }

    /**
     * Retrieves a warehouse location by its ID.
     *
     * @param id the ID of the warehouse location to retrieve.
     * @return ResponseEntity with the warehouse location details.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<WarehouseLocationResponseDTO>> getWarehouseLocation(UUID id) {
        log.info("Retrieving warehouse location with ID: {}", id);
        return repository.findById(id)
                .map(warehouseLocation -> ApiResponseDto.ok(isManager() ?
                        (WarehouseLocationResponseDTO) mapper.toDetailDTO(warehouseLocation) :
                        mapper.toBasicDTO(warehouseLocation)))
                .orElseThrow(() -> new EntityNotFoundException("Warehouse location not found with ID: " + id));
    }

    /**
     * Retrieves all warehouse locations.
     *
     * @return ResponseEntity with a list of all warehouse locations.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<List<WarehouseLocationResponseDTO>>> getAllWarehouseLocations(
            WarehouseLocationFilterDTO filterDTO, Pageable pageable
    ) {
        log.info("Retrieving all warehouse locations");
        Specification<WarehouseLocation> spec = WarehouseLocationSpecifications.applyFilters(filterDTO);
        List<WarehouseLocationResponseDTO> locations = repository.findAll(spec, pageable).stream()
                .map(warehouseLocation -> isManager() ?
                        (WarehouseLocationResponseDTO) mapper.toDetailDTO(warehouseLocation) :
                        mapper.toBasicDTO(warehouseLocation))
                .toList();
        return ApiResponseDto.ok(locations);
    }

    /**
     * Updates an existing warehouse location.
     *
     * @param id the ID of the warehouse location to update.
     * @param request the warehouse location request DTO containing the updated details.
     * @return ResponseEntity with the updated warehouse location details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<WarehouseLocationResponseDTO>> updateWarehouseLocation(
            UUID id, WarehouseLocationRequestDTO request) {
        log.info("Updating warehouse location with ID: {}", id);
        WarehouseLocation target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse location not found with ID: " + id));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(target)) :
                mapper.toBasicDTO(repository.save(target))
        );
    }

    /**
     * Deletes a warehouse location by its ID.
     *
     * @param id the ID of the warehouse location to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<Void>> deleteWarehouseLocation(UUID id) {
        log.info("Deleting warehouse location with ID: {}", id);
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
