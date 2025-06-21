package dev.austinbarnes.retailinventorymanagement.location.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeFilterDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationType;
import dev.austinbarnes.retailinventorymanagement.location.mapper.LocationTypeMapper;
import dev.austinbarnes.retailinventorymanagement.location.repo.LocationTypeRepository;
import dev.austinbarnes.retailinventorymanagement.location.specification.LocationTypeSpecifications;
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
public class LocationTypeService {
    private final LocationTypeRepository repository;
    private final LocationTypeMapper mapper;

    /**
     * Creates a new location type.
     *
     * @param request the location type request DTO containing the details of the location type to create.
     * @return ResponseEntity with the created location type details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<LocationTypeResponseDTO>> createLocationType(
            LocationTypeRequestDTO request) {
        log.info("Creating location type: {}", request);
        return ApiResponseDto.created(isManager()?
                mapper.toDetailDTO(repository.save(mapper.toEntity(request))) :
                mapper.toBasicDTO(repository.save(mapper.toEntity(request)))
        );
    }

    /**
     * Retrieves a location type by its ID.
     *
     * @param id the ID of the location type to retrieve.
     * @return ResponseEntity with the location type details.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<LocationTypeResponseDTO>> getLocationType(UUID id) {
        log.info("Retrieving location type with ID: {}", id);
        return repository.findById(id)
                .map(locationType -> ApiResponseDto.ok(isManager() ?
                        (LocationTypeResponseDTO) mapper.toDetailDTO(locationType) :
                        mapper.toBasicDTO(locationType)))
                .orElseThrow(() -> new EntityNotFoundException("Location type not found with ID: " + id));
    }

    /**
     * Retrieves all location types.
     *
     * @return ResponseEntity with a list of all location types.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<List<LocationTypeResponseDTO>>> getAllLocationTypes(
            LocationTypeFilterDTO filterDTO, Pageable pageable
    ) {
        log.info("Retrieving all location types");
        Specification<LocationType> spec = LocationTypeSpecifications.applyFilters(filterDTO);
        return ApiResponseDto.ok(repository.findAll(spec, pageable).stream()
                .map(locationType -> isManager() ?
                        (LocationTypeResponseDTO) mapper.toDetailDTO(locationType) :
                        mapper.toBasicDTO(locationType))
                .toList());
    }

    /**
     * Updates an existing location type.
     *
     * @param id      the ID of the location type to update.
     * @param request the location type request DTO containing the updated details.
     * @return ResponseEntity with the updated location type details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<LocationTypeResponseDTO>> updateLocationType(
            UUID id, LocationTypeRequestDTO request) {
        log.info("Updating location type with ID: {}", id);
        LocationType target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location type not found with ID: " + id));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(target)) :
                mapper.toBasicDTO(repository.save(target)));
    }

    /**
     * Deletes a location type by its ID.
     *
     * @param id the ID of the location type to delete.
     * @return ResponseEntity with no content.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN') and hasAuthority('WRITE_LOCATION')")
    public ResponseEntity<ApiResponseDto<Void>> deleteLocationType(UUID id) {
        log.info("Deleting location type with ID: {}", id);
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
