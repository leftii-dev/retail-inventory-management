package dev.austinbarnes.retailinventorymanagement.location.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.location.mapper.LocationMapper;
import dev.austinbarnes.retailinventorymanagement.location.repo.LocationRepository;
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
public class LocationService {
    private final LocationRepository repository;
    private final LocationMapper mapper;

    /**
     * Creates a new location.
     *
     * @param request the location request DTO containing the details of the location to create.
     * @return ResponseEntity with the created location details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<LocationResponseDTO>> createLocation(LocationRequestDTO request) {
        log.info("Creating new location: {}", request);
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(mapper.toEntity(request))) :
                mapper.toBasicDTO(repository.save(mapper.toEntity(request)))
        );
    }

    /**
     * Retrieves a location by its ID.
     *
     * @param id the ID of the location to retrieve.
     * @return ResponseEntity with the location details.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<LocationResponseDTO>> getLocationById(UUID id) {
        log.info("Retrieving location with ID: {}", id);
        return repository.findById(id)
                .map(location -> ApiResponseDto.ok(isManager() ?
                        (LocationResponseDTO) mapper.toDetailDTO(location) :
                        mapper.toBasicDTO(location)))
                .orElseThrow((
                ) -> new EntityNotFoundException("Location not found with ID: " + id));
    }

    /**
     * retrieves all locations.
     *
     * @return ResponseEntity with a list of all locations.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<List<LocationResponseDTO>>> getLocations() {
        log.info("Retrieving all locations");
        return ApiResponseDto.ok(repository.findAll().stream()
                .map(location -> isManager() ?
                        (LocationResponseDTO) mapper.toDetailDTO(location) : mapper.toBasicDTO(location))
                .toList());
    }

    /**
     * Updates an existing location.
     *
     * @param id      the ID of the location to update.
     * @param request the location request DTO containing the updated details of the location.
     * @return ResponseEntity with the updated location details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<LocationResponseDTO>> updateLocation(UUID id, LocationRequestDTO request) {
        log.info("Updating location with ID: {}", id);
        Location target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with id: %s".formatted(id)));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(target)) :
                mapper.toBasicDTO(repository.save(target)));
    }

    /**
     * Deletes a location by its ID.
     *
     * @param id the ID of the location to delete.
     * @return ResponseEntity with no content.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<Void>> deleteLocation(UUID id) {
        log.info("Deleting location with ID: {}", id);
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
