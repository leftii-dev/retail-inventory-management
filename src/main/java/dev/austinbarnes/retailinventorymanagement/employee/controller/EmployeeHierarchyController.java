package dev.austinbarnes.retailinventorymanagement.employee.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyFilterDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyResponseDTO;
import dev.austinbarnes.retailinventorymanagement.employee.service.EmployeeHierarchyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * EmployeeHierarchyController handles all employee hierarchy-related operations.
 * <p>
 * Provides endpoints to create, update, delete, and retrieve employee hierarchies.
 */
@RestController
@RequestMapping("api/v1/employee-manager")
@RequiredArgsConstructor
@Slf4j
public class EmployeeHierarchyController {
    private final EmployeeHierarchyService hierarchyService;

    /**
     * Creates a new employee hierarchy relationship.
     *
     * @param request The request DTO containing employee hierarchy details.
     * @return ResponseEntity with the created employee hierarchy details.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<EmployeeHierarchyResponseDTO>> createEmployeeHierarchy(
            @RequestBody @Valid EmployeeHierarchyRequestDTO request
    ) {
        log.info("Create employee hierarchy request: {}", request);
        return hierarchyService.createEmployeeHierarchy(request);
    }

    /**
     * Updates an existing employee hierarchy relationship.
     *
     * @param request The request DTO containing updated employee hierarchy details.
     * @return ResponseEntity with the updated employee hierarchy details.
     */
    @PutMapping
    public ResponseEntity<ApiResponseDto<EmployeeHierarchyResponseDTO>> updateEmployeeHierarchy(
            @RequestBody @Valid EmployeeHierarchyRequestDTO request
    ) {
        log.info("Update employee hierarchy request: {}", request);
        return hierarchyService.updateHierarchy(request);
    }

    /**
     * Retrieves the hierarchy of a specific employee.
     *
     * @param employeeId The ID of the employee whose hierarchy is to be retrieved.
     * @return ResponseEntity with the employee hierarchy details.
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<ApiResponseDto<EmployeeHierarchyResponseDTO>> getEmployeeHierarchy(@PathVariable UUID employeeId) {
        log.info("Get employee hierarchy request: {}", employeeId);
        return hierarchyService.getEmployeeHierarchy(employeeId);
    }

    /**
     * Retrieves the hierarchy of all employees.
     *
     * @return ResponseEntity with a list of all employee hierarchies.
     */
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<EmployeeHierarchyResponseDTO>>> getEmployeeHierarchyAll(
            @RequestBody(required = false) @Valid EmployeeHierarchyFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        log.info("Get employee hierarchy all");
        Pageable pageable = (page != null && size != null) ? PageRequest.of(page, size) : Pageable.unpaged();
        return hierarchyService.getEmployeeHierarchyAll(filterDTO, pageable);
    }

    /**
     * Deletes an employee hierarchy relationship.
     *
     * @param relationshipId The ID of the relationship to be deleted.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @DeleteMapping("/{relationshipId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteEmployeeHierarchyRelationship(@PathVariable UUID relationshipId) {
        log.info("Delete hierarchy relationship {}", relationshipId);
        return hierarchyService.deleteEmployeeHierarchyRelationship(relationshipId);
    }
}
