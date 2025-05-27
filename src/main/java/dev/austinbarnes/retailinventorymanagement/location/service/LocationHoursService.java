package dev.austinbarnes.retailinventorymanagement.location.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.location.dto.hours.LocationHoursRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.hours.LocationHoursResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationHours;
import dev.austinbarnes.retailinventorymanagement.location.mapper.LocationHoursMapper;
import dev.austinbarnes.retailinventorymanagement.location.repo.LocationHoursRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

/**
 * Service class for managing location hours.
 * Provides methods to create, retrieve, update, and delete location hours.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LocationHoursService {
    private final LocationHoursRepository repository;
    private final LocationHoursMapper mapper;

    /**
     * Creates new location hours.
     *
     * @param request the location hours request DTO containing the details of the hours to create.
     * @return ResponseEntity with the created location hours details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<LocationHoursResponseDTO>> createLocationHours(
            @RequestBody LocationHoursRequestDTO request) {
        log.info("Creating location hours for location: {}", request);
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(mapper.toEntity(request))) :
                mapper.toBasicDTO(repository.save(mapper.toEntity(request)))
        );
    }

    /**
     * Retrieves location hours by ID.
     *
     * @param id the ID of the location hours to retrieve.
     * @return ResponseEntity with the location hours details.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<LocationHoursResponseDTO>> getLocationHoursById(UUID id) {
        log.info("Retrieving location hours with ID: {}", id);
        return repository.findById(id)
                .map(locationHours -> ApiResponseDto.ok(isManager() ?
                        (LocationHoursResponseDTO) mapper.toDetailDTO(locationHours) :
                        mapper.toBasicDTO(locationHours)))
                .orElseThrow(() -> new EntityNotFoundException("Location hours not found with ID: " + id));
    }

    /**
     * Retrieves all location hours.
     *
     * @return ResponseEntity with a list of all location hours.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<List<LocationHoursResponseDTO>>> getAllLocationHours() {
        log.info("Retrieving all location hours");
        List<LocationHours> hoursList = repository.findAll();
        return ApiResponseDto.ok(hoursList.stream()
                .map(locationHours -> isManager() ?
                        (LocationHoursResponseDTO) mapper.toDetailDTO(locationHours) :
                        mapper.toBasicDTO(locationHours))
                .toList());
    }

    /**
     * Updates existing location hours.
     *
     * @param id      the ID of the location hours to update.
     * @param request the location hours request DTO containing the updated details.
     * @return ResponseEntity with the updated location hours details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<LocationHoursResponseDTO>> updateLocationHours(
            UUID id, @RequestBody LocationHoursRequestDTO request) {
        log.info("Updating location hours with ID: {}", id);
        LocationHours target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location hours not found with ID: " + id));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(target)) :
                mapper.toBasicDTO(repository.save(target))
        );
    }

    /**
     * Deletes location hours by ID.
     *
     * @param id the ID of the location hours to delete.
     * @return ResponseEntity with no content.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<Void>> deleteLocationHours(UUID id) {
        log.info("Deleting location hours with ID: {}", id);
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
