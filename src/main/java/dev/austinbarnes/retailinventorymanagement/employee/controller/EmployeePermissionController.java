package dev.austinbarnes.retailinventorymanagement.employee.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.EmployeePermissionFilterDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.EmployeePermissionRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.EmployeePermissionResponseDTO;
import dev.austinbarnes.retailinventorymanagement.employee.service.EmployeePermissionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * EmployeePermissionController handles HTTP requests related to employee permissions.
 * <p>
 * It provides endpoints to create, update, retrieve, and delete employee permission relationships.
 */
@RestController
@RequestMapping("api/v1/employee-permissions")
@AllArgsConstructor
@Slf4j
public class EmployeePermissionController {
    private final EmployeePermissionService service;

    /**
     * Creates a new employee permission.
     *
     * @param employeePermission the employee permission request DTO
     * @return ResponseEntity with the created employee permission details
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<EmployeePermissionResponseDTO>> createEmployeePermission(@RequestBody @Valid EmployeePermissionRequestDTO employeePermission) {
        log.info("Creating employee permissions with employee ID: {} permission ID: {}", employeePermission.employeeID(), employeePermission.roleID());
        return service.createEmployeePermission(employeePermission);
    }

    /**
     * Updates an existing employee permission.
     *
     * @param employeePermission the employee permission request DTO
     * @return ResponseEntity with the updated employee permission details
     */
    @PutMapping
    public ResponseEntity<ApiResponseDto<EmployeePermissionResponseDTO>> updateEmployeePermission(@RequestBody @Valid EmployeePermissionRequestDTO employeePermission) {
        log.info("Updating employee permissions with employee ID: {} permission ID: {}", employeePermission.employeeID(), employeePermission.roleID());
        return service.updateEmployeePermission(employeePermission);
    }

    /**
     * Retrieves an employee permission by ID.
     *
     * @param permissionId the ID of the employee permission to retrieve
     * @return ResponseEntity with the result of the deletion
     */
    @GetMapping("/{permissionId}")
    public ResponseEntity<ApiResponseDto<EmployeePermissionResponseDTO>> getEmployeePermission(@PathVariable UUID permissionId) {
        log.info("Getting employee permissions with permission ID: {}", permissionId);
        return service.getEmployeePermissionById(permissionId);
    }

    /**
     * Requests all Employee Permission relationships allows filtering
     * @param filterDTO Used to filter values
     * @param page number of page to retrieve
     * @param size size of page to retrieve
     * @param sortBy allows sort by parameter
     * @param sortDirection ASC or DESC
     * @return List of all filtered EmployeePermission Objects
     */
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<EmployeePermissionResponseDTO>>> getAllEmployeePermissions(
            @ModelAttribute EmployeePermissionFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
            ) {
        Sort sort = (sortBy != null && sortDirection != null
                ? Sort.by(Sort.Direction.fromString(sortDirection), sortBy)
                : Sort.unsorted());
        Pageable pageable = (page != null && size != null
                ? PageRequest.of(page, size, sort)
                : Pageable.unpaged(sort));
        return service.getAllEmployeePermissions(filterDTO, pageable);
    }

    /**
     * Deletes an employee permission by ID.
     *
     * @param permissionId the ID of the employee permission to delete
     * @return ResponseEntity with the result of the deletion
     */
    @DeleteMapping("/{permissionId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteEmployeePermission(@PathVariable UUID permissionId) {
        log.info("Deleting employee permissions with permission ID: {}", permissionId);
        return service.deleteEmployeePermissionById(permissionId);
    }
}
