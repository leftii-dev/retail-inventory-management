package dev.austinbarnes.retailinventorymanagement.employee.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.*;
import dev.austinbarnes.retailinventorymanagement.employee.service.EmployeeService;
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
 * EmployeeController handles all employee-related operations.
 * <p>
 * Provides endpoints to create, update, delete, and retrieve employees.
 */
@RestController
@RequestMapping("api/v1/employees")
@Slf4j
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    /**
     * Creates a new employee.
     *
     * @param request   The employee request DTO containing employee details.
     * @param managerId Optional manager ID for the new employee.
     * @return ResponseEntity with the created employee details.
     */
    @Operation(
            summary = "Create New Employee",
            description = "Authenticates an employee with their credentials.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Employee created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {EmployeeResponseBasicDTO.class, EmployeeResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or employee already exists"),
            @ApiResponse(responseCode = "404", description = "Manager not found"),

    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> createEmployee(
            @RequestBody @Valid EmployeeRequestDTO request,
            @RequestParam(required = false) UUID managerId) {

        log.info("Create employee request: {}", request);

        return managerId != null
                ?
                employeeService.createEmployeeWithManager(request, managerId)
                :
                employeeService.createEmployee(request);
    }

    /**
     * Retrieves all employees.
     *
     * @return ResponseEntity with a list of all employees.
     */
    @Operation(
            summary = "Employee Login",
            description = "Authenticates an employee with their credentials.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employees retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {EmployeeResponseDetailDTO.class, EmployeeResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<EmployeeResponseDTO>>> getAllEmployees(
            @ModelAttribute @Valid EmployeeFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection) {

        log.info("Get all employees");
        Sort sort = (sortBy != null && sortDirection != null)
                ? Sort.by(Sort.Direction.fromString(sortDirection), sortBy)
                : Sort.unsorted();

        Pageable pageable = (page != null && size != null) ? PageRequest.of(page, size, sort) : Pageable.unpaged();
        return employeeService.getAllEmployees(filterDTO, pageable);
    }

    /**
     * Retrieves an employee by ID.
     *
     * @param employeeId The ID of the employee to retrieve.
     * @return ResponseEntity with the employee details.
     */
    @Operation(
            summary = "Get Employee by ID",
            description = "Retrieves an employee by their unique ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {EmployeeResponseBasicDTO.class, EmployeeResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/{employeeId}")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> getEmployeeById(@PathVariable UUID employeeId) {
        log.info("Get employee by id: {}", employeeId);
        return employeeService.getEmployeeById(employeeId);
    }

    /**
     * Updates an existing employee.
     *
     * @param employeeId The ID of the employee to update.
     * @param request    The employee request DTO containing updated employee details.
     * @return ResponseEntity with the updated employee details.
     */
    @Operation(
            summary = "Update Employee",
            description = "Updates an existing employee's details.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {EmployeeResponseBasicDTO.class, EmployeeResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @PutMapping("/{employeeId}")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> updateEmployee(
            @PathVariable UUID employeeId,
            @RequestBody @Valid EmployeeRequestDTO request) {
        log.info("Update employeeId: {} with request: {}", employeeId, request);
        return employeeService.updateEmployee(employeeId, request);
    }

    /**
     * Deletes an employee by ID.
     *
     * @param employeeId The ID of the employee to delete.
     * @return ResponseEntity with no content.
     */
    @Operation(
            summary = "Delete Employee",
            description = "Deletes an employee by their unique ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Employee deleted successfully",
                    content =  {@Content(mediaType = "application/json", schema = @Schema(oneOf = {Void.class})) }
            )
    })
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteEmployee(@PathVariable UUID employeeId) {
        log.info("Delete employee {}", employeeId);
        return employeeService.deleteEmployee(employeeId);
    }
}

