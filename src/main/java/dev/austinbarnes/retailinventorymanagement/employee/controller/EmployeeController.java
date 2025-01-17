package dev.austinbarnes.retailinventorymanagement.employee.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyResponseDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeRepository;
import dev.austinbarnes.retailinventorymanagement.employee.service.EmployeeHierarchyService;
import dev.austinbarnes.retailinventorymanagement.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;



@RestController
@RequestMapping("api/v1/employees")
@Slf4j
@RequiredArgsConstructor
/*
 * Controller for Employee Entity as well as EmployeeHierarchy
 */
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeHierarchyService hierarchyService;

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

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<EmployeeResponseDTO>>> getAllEmployees() {
        log.info("Get all employees");
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> getEmployeeById(@PathVariable UUID employeeId) {
        log.info("Get employee by id: {}", employeeId);
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> updateEmployee(@PathVariable UUID employeeId, @RequestBody @Valid EmployeeRequestDTO request) {
        log.info("Update employeeId: {} with request: {}", employeeId, request);
        return employeeService.updateEmployee(employeeId, request);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteEmployee(@PathVariable UUID employeeId) {
        log.info("Delete employee {}", employeeId);
        return employeeService.deleteEmployee(employeeId);
    }

    @PostMapping("/hierarchy")
    public ResponseEntity<ApiResponseDto<EmployeeHierarchyResponseDTO>> createEmployeeHierarchy(
            @RequestBody @Valid EmployeeHierarchyRequestDTO request
    ) {
        log.info("Create employee hierarchy request: {}", request);
        return hierarchyService.createEmployeeHierarchy(request);
    }

    @PutMapping("/hierarchy")
    public ResponseEntity<ApiResponseDto<EmployeeHierarchyResponseDTO>> updateEmployeeHierarchy(
            @RequestBody @Valid EmployeeHierarchyRequestDTO request
    ) {
        log.info("Update employee hierarchy request: {}", request);
        return hierarchyService.updateHierarchy(request);
    }

    @GetMapping("/hierarchy/{employeeId}")
    public ResponseEntity<ApiResponseDto<EmployeeHierarchyResponseDTO>> getEmployeeHierarchy(@PathVariable UUID employeeId) {
        log.info("Get employee hierarchy request: {}", employeeId);
        return hierarchyService.getEmployeeHierarchy(employeeId);
    }

    @GetMapping("/hierarchy")
    public ResponseEntity<ApiResponseDto<List<EmployeeHierarchyResponseDTO>>> getEmployeeHierarchyAll() {
        log.info("Get employee hierarchy all");
        return hierarchyService.getEmployeeHierarchyAll();
    }

    @DeleteMapping("/hierarchy")
    public ResponseEntity<ApiResponseDto<Void>> deleteEmployeeHierarchyRelationship(UUID employeeHierarchyId) {
        log.info("Delete hierarchy relationship {}", employeeHierarchyId);
        return hierarchyService.deleteEmployeeHierarchyRelationship(employeeHierarchyId);
    }
}

