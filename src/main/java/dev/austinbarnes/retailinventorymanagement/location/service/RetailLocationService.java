package dev.austinbarnes.retailinventorymanagement.location.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.RetailLocation;
import dev.austinbarnes.retailinventorymanagement.location.mapper.RetailLocationMapper;
import dev.austinbarnes.retailinventorymanagement.location.repo.RetailLocationRepository;
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
public class RetailLocationService {
    private final RetailLocationRepository repository;
    private final RetailLocationMapper mapper;

    /**
     * Creates a new retail location.
     *
     * @param request the retail location request DTO containing the details of the retail location to create.
     * @return ResponseEntity with the created retail location details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<RetailLocationResponseDTO>> createRetailLocation(
            RetailLocationRequestDTO request) {
        log.info("Creating new retail location: {}", request);
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(mapper.toEntity(request))) :
                mapper.toBasicDTO(repository.save(mapper.toEntity(request)))
        );
    }

    /**
     * Retrieves a retail location by its ID.
     *
     * @param id the ID of the retail location to retrieve.
     * @return ResponseEntity with the retail location details.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<RetailLocationResponseDTO>> getRetailLocation(UUID id) {
        log.info("Retrieving retail location with ID: {}", id);
        return repository.findById(id)
                .map(retailLocation -> ApiResponseDto.ok(isManager() ?
                        (RetailLocationResponseDTO) mapper.toDetailDTO(retailLocation) :
                        mapper.toBasicDTO(retailLocation)))
                .orElseThrow(() -> new EntityNotFoundException("Retail location not found with ID: " + id));
    }

    /**
     * Retrieves list of all retail locations.
     *
     * @return ResponseEntity with the list of all retail locations.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<List<RetailLocationResponseDTO>>> getAllRetailLocations() {
        log.info("Retrieving all retail locations");
        return ApiResponseDto.ok(repository.findAll().stream()
                .map(location -> isManager() ?
                        (RetailLocationResponseDTO) mapper.toDetailDTO(location) :
                        mapper.toBasicDTO(location))
                .toList());
    }

    /**
     * Updates an existing retail location.
     *
     * @param id      the ID of the retail location to update.
     * @param request the retail location request DTO containing the updated details.
     * @return ResponseEntity with the updated retail location details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<RetailLocationResponseDTO>> updateRetailLocation(
            UUID id, RetailLocationRequestDTO request) {
        log.info("Updating retail location with ID: {}", id);
        RetailLocation target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Retail location not found with ID: " + id));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(target)) :
                mapper.toBasicDTO(repository.save(target)));
    }

    /**
     * Deletes a retail location by its ID.
     *
     * @param id the ID of the retail location to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<Void>> deleteRetailLocation(UUID id) {
        log.info("Deleting retail location with ID: {}", id);
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
