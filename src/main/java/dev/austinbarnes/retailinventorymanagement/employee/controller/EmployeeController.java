package dev.austinbarnes.retailinventorymanagement.employee.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyResponseDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;



@RestController("/employees")
@Slf4j
@RequiredArgsConstructor
/*
 * Controller for Employee Entity as well as EmployeeHierarchy
 */
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> createEmployee(
            @RequestBody @Valid EmployeeRequestDTO request,
            @RequestParam(required = false) UUID managerID) {

        log.info("Create employee request: {}", request);

        return managerID != null
                ?
                employeeService.createEmployeeWithManager(request, managerID)
                :
                employeeService.createEmployee(request);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<EmployeeResponseDTO>>> getAllEmployees() {
        log.info("Get all employees");
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{employeeID}")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> getEmployeeById(@PathVariable UUID employeeID) {
        log.info("Get employee by id: {}", employeeID);
        return employeeService.getEmployeeById(employeeID);
    }

    @PutMapping("/{employeeID}")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> updateEmployee(@PathVariable UUID employeeID, @RequestBody @Valid EmployeeRequestDTO request) {
        log.info("Update employee {} request: {}", employeeID, request);
        return employeeService.updateEmployee(employeeID, request);
    }

    @DeleteMapping("/{employeeID}")
    public ResponseEntity<ApiResponseDto<Void>> deleteEmployee(@PathVariable UUID employeeID) {
        log.info("Delete employee {}", employeeID);
        return employeeService.deleteEmployee(employeeId);
    }

    @PostMapping("/hierarchy")
    public ResponseEntity<ApiResponseDto<EmployeeHierarchyResponseDTO>> createEmployeeHierarchy(
            @RequestBody @Valid EmployeeHierarchyRequestDTO request
    ) {
        log.info("Create employee hierarchy request: {}", request);
        return employeeService.createEmployeeHierarchy(request);
    }

    @PutMapping("/hierarchy")
    public ResponseEntity<ApiResponseDto<EmployeeHierarchyResponseDTO>> updateEmployeeHierarchy(
            @RequestBody @Valid EmployeeHierarchyRequestDTO request
    ) {
        log.info("Update employee hierarchy request: {}", request);
        return employeeService.updateHierarchy(request);
    }

    @GetMapping("/hierarchy/{userID}")
    public ResponseEntity<ApiResponseDto<List<EmployeeHierarchyResponseDTO>>> getEmployeeHierarchy(@PathVariable UUID userID) {
        log.info("Get employee hierarchy request: {}", userID);
        return employeeService.getEmployeeHierarchy(userID);
    }

    @GetMapping("/hierarchy")
    public ResponseEntity<ApiResponseDto<List<EmployeeHierarchyResponseDTO>>> getEmployeeHierarchyAll() {
        log.info("Get employee hierarchy all");
        return employeeService.getEmployeeHierarchyAll();
    }

    @DeleteMapping("/hierarchy")
    public ResponseEntity<ApiResponseDto<Void>> deleteEmployeeHierarchyRelationship(@RequestBody @Valid EmployeeHierarchyRequestDTO request) {
        log.info("Delete employee hierarchy relationship {}", request);
        return employeeService.deleteEmployeeHierarchyRelationship(request);
    }
}
