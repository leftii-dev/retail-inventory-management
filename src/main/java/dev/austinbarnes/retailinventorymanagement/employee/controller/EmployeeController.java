package dev.austinbarnes.retailinventorymanagement.employee.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeFilterDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;
import dev.austinbarnes.retailinventorymanagement.employee.service.EmployeeService;
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
        @GetMapping("/{employeeId}")
        public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> getEmployeeById (@PathVariable UUID employeeId){
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
        @PutMapping("/{employeeId}")
        public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> updateEmployee (
                @PathVariable UUID employeeId,
                @RequestBody @Valid EmployeeRequestDTO request){
            log.info("Update employeeId: {} with request: {}", employeeId, request);
            return employeeService.updateEmployee(employeeId, request);
        }

        /**
         * Deletes an employee by ID.
         *
         * @param employeeId The ID of the employee to delete.
         * @return ResponseEntity with no content.
         */
        @DeleteMapping("/{employeeId}")
        public ResponseEntity<ApiResponseDto<Void>> deleteEmployee (@PathVariable UUID employeeId){
            log.info("Delete employee {}", employeeId);
            return employeeService.deleteEmployee(employeeId);
        }
    }

