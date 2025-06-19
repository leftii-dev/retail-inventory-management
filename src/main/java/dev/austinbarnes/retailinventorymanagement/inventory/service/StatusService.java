package dev.austinbarnes.retailinventorymanagement.inventory.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusFilterDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Status;
import dev.austinbarnes.retailinventorymanagement.inventory.mapper.StatusMapper;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.StatusRepository;
import dev.austinbarnes.retailinventorymanagement.inventory.specification.StatusSpecifications;
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
public class StatusService {
    private final StatusRepository repository;
    private final StatusMapper mapper;

    /**
     * Creates a new status.
     *
     * @param request the status request DTO containing the details of the status to create.
     * @return ResponseEntity with the created status details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_STATUS')")
    public ResponseEntity<ApiResponseDto<StatusResponseDTO>> createStatus(StatusRequestDTO request) {
        log.info("Creating new status: {}", request);
        return ApiResponseDto.created(
            isManager() ?
                mapper.toDetailDTO(repository.save(mapper.toEntity(request))) :
                mapper.toBasicDTO(repository.save(mapper.toEntity(request)))
        );
    }

    /**
     * Retrieves a status by its ID.
     *
     * @param id the ID of the status to retrieve.
     * @return ResponseEntity with the status details.
     */
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('READ_STATUS')")
    public ResponseEntity<ApiResponseDto<StatusResponseDTO>> getStatusById(UUID id) {
        log.info("Retrieving status with ID: {}", id);
        return repository.findById(id)
                .map(status -> ApiResponseDto.ok(isManager() ?
                        (StatusResponseDTO) mapper.toDetailDTO(status) :
                        mapper.toBasicDTO(status)))
                .orElseThrow(() -> new EntityNotFoundException("Status not found with ID: " + id));
    }

    /**
     * Retrieves all statuses.
     *
     * @return ResponseEntity with a list of all statuses.
     */
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('READ_STATUS')")
    public ResponseEntity<ApiResponseDto<List<StatusResponseDTO>>> getAllStatuses(
            StatusFilterDTO filterDTO, Pageable pageable
    ) {
        log.info("Retrieving all statuses");
        Specification<Status> spec = StatusSpecifications.applyFilters(filterDTO);
        return ApiResponseDto.ok(repository.findAll(spec, pageable).stream()
                .map(status -> isManager() ?
                        (StatusResponseDTO) mapper.toDetailDTO(status) :
                        mapper.toBasicDTO(status))
                .toList());
    }

    /**
     * Updates an existing status.
     *
     * @param id      the ID of the status to update.
     * @param request the status request DTO containing the updated details.
     * @return ResponseEntity with the updated status details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_STATUS')")
    public ResponseEntity<ApiResponseDto<StatusResponseDTO>> updateStatus(UUID id, StatusRequestDTO request) {
        log.info("Updating status with ID: {}", id);
        Status target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status not found with ID: " + id));

        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(target)) :
                mapper.toBasicDTO(repository.save(target)));
    }

    /**
     * Deletes a status by its ID.
     *
     * @param id the ID of the status to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_STATUS')")
    public ResponseEntity<ApiResponseDto<Void>> deleteStatus(UUID id) {
        log.info("Deleting status with ID: {}", id);
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Status not found with ID: " + id);
        }
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
