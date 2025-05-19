package dev.austinbarnes.retailinventorymanagement.employee.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.EmployeePermissionRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.EmployeePermissionResponseDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeePermission;
import dev.austinbarnes.retailinventorymanagement.employee.mapper.EmployeePermissionMapper;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeePermissionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * EmployeePermissionService handles all operations related to employee permissions.
 * <p>
 * It provides methods to create, update, retrieve, and delete employee permission relationships.
 */
@Service
@AllArgsConstructor
@Slf4j
@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
public class EmployeePermissionService {
    private final EmployeePermissionRepository repository;
    private final EmployeePermissionMapper mapper;

    /**
     * Creates a new employee permission relationship.
     *
     * @param request The request DTO containing employee permission details.
     */
    public ResponseEntity<ApiResponseDto<EmployeePermissionResponseDTO>> createEmployeePermission(
            EmployeePermissionRequestDTO request
    ) {
        log.info("Creating employee permission for employee ID: {} and permission ID: {}", request.employeeID(), request.roleID());
        return ApiResponseDto.created(mapper.toDetailDTO(repository.save(mapper.toEntity(request))));
    }

    /**
     * Updates an existing employee permission relationship.
     *
     * @param request The request DTO containing updated employee permission details.
     */
    public ResponseEntity<ApiResponseDto<EmployeePermissionResponseDTO>> updateEmployeePermission(EmployeePermissionRequestDTO request) {
        log.info("Updating employee permission for employee ID: {} and permission ID: {}", request.employeeID(), request.roleID());
        EmployeePermission target = repository.findById(request.roleID())
                .orElseThrow(() -> new EntityNotFoundException("Employee permission with ID: %s not found".formatted(request.roleID())));
        return ApiResponseDto.ok(mapper.toDetailDTO(repository.save(target)));
    }

    /**
     * Retrieves employee permission relationship by ID.
     *
     * @param id The ID of the employee permission to retrieve.
     */
    public ResponseEntity<ApiResponseDto<EmployeePermissionResponseDTO>> getEmployeePermissionById(UUID id) {
        log.info("Getting employee permission by ID: {}", id);
        EmployeePermission permission = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee permission with ID: %s not found".formatted(id)));
        return ApiResponseDto.ok(mapper.toDetailDTO(permission));
    }

    /**
     * Retrieves all employee permission relationships.
     */
    public ResponseEntity<ApiResponseDto<List<EmployeePermissionResponseDTO>>> getAllEmployeePermissions() {
        log.info("Getting all employee permissions");
        return ApiResponseDto.ok(repository.findAll().stream()
                .map(employeePermission -> (EmployeePermissionResponseDTO) mapper.toDetailDTO(employeePermission))
                .toList());
    }

    /**
     * Retrieves all employee permissions by employee ID.
     *
     * @param employeeId The ID of the employee to retrieve permissions for.
     */
    public ResponseEntity<ApiResponseDto<List<EmployeePermissionResponseDTO>>> getAllEmployeePermissionsByEmployeeId(UUID employeeId) {
        log.info("Getting all employee permissions by employee ID: {}", employeeId);
        return ApiResponseDto.ok(repository.findAllByEmployeeId(employeeId).stream()
                .map(employeePermission -> (EmployeePermissionResponseDTO) mapper.toDetailDTO(employeePermission))
                .toList());
    }

    /**
     * Deletes an employee permission relationship by ID.
     *
     * @param id The ID of the employee permission to delete.
     */
    public ResponseEntity<ApiResponseDto<Void>> deleteEmployeePermissionById(UUID id) {
        log.info("Deleting employee permission by ID: {}", id);
        EmployeePermission permission = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee permission with ID: %s not found".formatted(id)));
        repository.delete(permission);
        return ApiResponseDto.noContent();
    }

}
