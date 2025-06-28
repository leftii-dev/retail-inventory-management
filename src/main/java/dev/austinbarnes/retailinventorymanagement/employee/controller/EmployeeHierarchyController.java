package dev.austinbarnes.retailinventorymanagement.employee.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.*;
import dev.austinbarnes.retailinventorymanagement.employee.service.EmployeeHierarchyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Operation(
            summary = "Create Employee Hierarchy",
            description = "Creates a new employee hierarchy relationship."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Employee hierarchy created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {EmployeeHierarchyResponseBasicDTO.class, EmployeeHierarchyResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
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
    @Operation(
            summary = "Update Employee Hierarchy",
            description = "Updates an existing employee hierarchy relationship.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee hierarchy updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {EmployeeHierarchyResponseBasicDTO.class, EmployeeHierarchyResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
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
    @Operation(
            summary = "Get Employee Hierarchy",
            description = "Retrieves the hierarchy of a specific employee.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee hierarchy retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {EmployeeHierarchyResponseBasicDTO.class, EmployeeHierarchyResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/{employeeId}")
    public ResponseEntity<ApiResponseDto<EmployeeHierarchyResponseDTO>> getEmployeeHierarchy(@PathVariable UUID employeeId) {
        log.info("Get employee hierarchy request: {}", employeeId);
        return hierarchyService.getEmployeeHierarchy(employeeId);
    }

    /**
     * Retrieves the hierarchy of all employees.
     *
     * @param filterDTO HierarchyFilterDTO for filtering data.
     * @param page Index of paged data.
     * @param size Size of list to retrieve.
     * @param sortBy Allows sorting by parameters.
     * @param sortDirection ASC or DESC sorting the data.
     * @return List of EmployeeHierarchyDTO.
     */
    @Operation(
            summary = "Get All Employee Hierarchies",
            description = "Retrieves the hierarchy of all employees with optional filtering and pagination."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee hierarchies retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {EmployeeHierarchyResponseBasicDTO.class, EmployeeHierarchyResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<EmployeeHierarchyResponseDTO>>> getEmployeeHierarchyAll(
            @ModelAttribute @Valid EmployeeHierarchyFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection) {
        log.info("Get employee hierarchy all");
        Sort sort = (sortBy != null && sortDirection != null)
                ? Sort.by(Sort.Direction.fromString(sortDirection), sortBy)
                : Sort.unsorted();

        Pageable pageable = (page != null && size != null) ? PageRequest.of(page, size, sort) : Pageable.unpaged();
        return hierarchyService.getEmployeeHierarchyAll(filterDTO, pageable);
    }

    /**
     * Deletes an employee hierarchy relationship.
     *
     * @param relationshipId The ID of the relationship to be deleted.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Operation(
            summary = "Delete Employee Hierarchy Relationship",
            description = "Deletes an employee hierarchy relationship by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Employee hierarchy relationship deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {Void.class})
                    )
            )
    })
    @DeleteMapping("/{relationshipId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteEmployeeHierarchyRelationship(@PathVariable UUID relationshipId) {
        log.info("Delete hierarchy relationship {}", relationshipId);
        return hierarchyService.deleteEmployeeHierarchyRelationship(relationshipId);
    }
}
