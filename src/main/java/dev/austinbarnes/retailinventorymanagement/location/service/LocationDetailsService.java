package dev.austinbarnes.retailinventorymanagement.location.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.location.dto.details.LocationDetailsRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.details.LocationDetailsResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationDetails;
import dev.austinbarnes.retailinventorymanagement.location.mapper.LocationDetailsMapper;
import dev.austinbarnes.retailinventorymanagement.location.repo.LocationDetailsRepository;
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
 * Service class for managing location details.
 * Provides methods to create, retrieve, update, and delete location details.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LocationDetailsService {
    private final LocationDetailsRepository repository;
    private final LocationDetailsMapper mapper;

    /**
     * Creates a new location details entry.
     *
     * @param request the location details request DTO containing the details of the location to create.
     * @return ResponseEntity with the created location details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN', 'MANAGER') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<LocationDetailsResponseDTO>> createLocationDetails(LocationDetailsRequestDTO request) {
        log.info("Creating new location details: {}", request);
        return ApiResponseDto.created(
                isManager() ?
                        mapper.toDetailDTO(repository.save(mapper.toEntity(request))) :
                        mapper.toBasicDTO(repository.save(mapper.toEntity(request)))
        );
    }

    /**
     * Retrieves location details by its ID.
     *
     * @param id the ID of the location details to retrieve.
     * @return ResponseEntity with the location details.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<LocationDetailsResponseDTO>> getLocationDetails(UUID id) {
        return repository.findById(id).map(details -> ApiResponseDto.ok(
                isManager() ?
                        (LocationDetailsResponseDTO) mapper.toDetailDTO(details) :
                        mapper.toBasicDTO(details)
        )).orElseThrow(() -> new EntityNotFoundException("Location details not found with ID: " + id));
    }

    /**
     * Retrieves all location details.
     *
     * @return ResponseEntity with a list of all location details.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<List<LocationDetailsResponseDTO>>> getAllLocationDetails() {
        log.info("Retrieving all location details");
        return ApiResponseDto.ok(repository.findAll().stream()
                .map(details -> isManager() ?
                        (LocationDetailsResponseDTO) mapper.toDetailDTO(details) :
                        mapper.toBasicDTO(details)
                )
                .toList());
    }

    /**
     * Updates an existing location details entry.
     *
     * @param id      the ID of the location details to update.
     * @param request the location details request DTO containing the updated details.
     * @return ResponseEntity with the updated location details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN', 'MANAGER') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<LocationDetailsResponseDTO>> updateLocationDetails(UUID id, LocationDetailsRequestDTO request) {
        log.info("Updating location details with ID: {}", id);
        LocationDetails target = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Location details not found with ID: " + id));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(
                isManager() ?
                        mapper.toDetailDTO(repository.save(target)) :
                        mapper.toBasicDTO(repository.save(target))
        );
    }

    /**
     * Deletes a location details entry by its ID.
     *
     * @param id the ID of the location details to delete.
     * @return ResponseEntity with no content.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN', 'MANAGER') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<Void>> deleteLocationDetails(UUID id) {
        log.info("Deleting location details with ID: {}", id);
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
