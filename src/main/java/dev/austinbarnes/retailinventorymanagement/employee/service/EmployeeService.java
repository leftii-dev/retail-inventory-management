package dev.austinbarnes.retailinventorymanagement.employee.service;

import dev.austinbarnes.retailinventorymanagement.auth.CustomUserPrincipal;
import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.auth.repo.UserRepository;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.*;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.employee.mapper.EmployeeMapper;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeRepository;
import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.entitycode.CodeGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * EmployeeService handles all operations related to employee management.
 * <p>
 * It provides methods to create, update, retrieve, and delete employee records.
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeHierarchyService employeeHierarchyService;
    private final EmployeeMapper mapper;
    private final UserRepository userRepository;
    private final CodeGenerator codeGenerator;

    /**
     * Creates a new employee record.
     *
     * @param employeeRequestDTO The request DTO containing employee details.
     * @return ResponseEntity with the created employee details.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> createEmployee(EmployeeRequestDTO employeeRequestDTO) {
        // Check if user already assigned to Employee object, return bad request, if so
        if (employeeRepository.findByUserId(employeeRequestDTO.userId()).isPresent()) {
            return ApiResponseDto.badRequest("Employee already exists for this User: %s".formatted(employeeRequestDTO.userId()));
        }

        //Create new Employee object return detail DTO as endpoint is only reachable by manager/admin
        Employee newEmployee = mapper.toEntity(employeeRequestDTO);
        newEmployee.setEmployeeCode(codeGenerator.generateEmployeeCode());
        newEmployee.setUser(userRepository.findById(employeeRequestDTO.userId()).orElseThrow(() -> new EntityNotFoundException("User not found")));
        return ApiResponseDto.created(mapper.toDetailDTO(employeeRepository.save(newEmployee)));
    }

    /**
     * Creates a new employee record with a manager.
     *
     * @param employeeRequestDTO The request DTO containing employee details.
     * @param managerID          The ID of the manager.
     * @return ResponseEntity with the created employee details.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> createEmployeeWithManager(EmployeeRequestDTO employeeRequestDTO, UUID managerID) {
        // Check if Entity exists already
        if (employeeRepository.findByUserId(employeeRequestDTO.userId()).isPresent()) {
            return ApiResponseDto.badRequest("Employee already exists for this User: %s".formatted(employeeRequestDTO.userId()));
        }

        Employee newEmployee = mapper.toEntity(employeeRequestDTO);
        newEmployee.setEmployeeCode(codeGenerator.generateEmployeeCode());
        newEmployee.setUser(userRepository.findById(employeeRequestDTO.userId()).orElseThrow(() -> new EntityNotFoundException("User not found")));
        EmployeeResponseDTO responseDTO = mapper.toDetailDTO(employeeRepository.save(newEmployee));
        EmployeeHierarchyRequestDTO hierarchyRequestDTO = new EmployeeHierarchyRequestDTO(responseDTO.id(), managerID);
        employeeHierarchyService.createEmployeeHierarchy(hierarchyRequestDTO);

        return ApiResponseDto.created(responseDTO);
    }

    /**
     * Retrieves all employees.
     *
     * @return ResponseEntity with a list of employee details.
     */
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponseDto<List<EmployeeResponseDTO>>> getAllEmployees() {
        if(isManager()){
            return ApiResponseDto.ok(employeeRepository.findAllByOrderByActiveDescNameLastAsc().stream()
                    .map(employee -> (EmployeeResponseDTO) mapper.toDetailDTO(employee))
                    .toList());
        }
            return ApiResponseDto.ok(employeeRepository.findAllByActiveTrue().stream()
                    .map(employee -> (EmployeeResponseDTO) mapper.toBasicDTO(employee))
                    .toList());
    }

    /**
     * Retrieves an employee by ID.
     *
     * @param employeeId The ID of the employee.
     * @return ResponseEntity with the employee details.
     */
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> getEmployeeById(UUID employeeId) {
        return ApiResponseDto.ok(employeeRepository.findById(employeeId).map(employee -> isManager() ? mapper.toDetailDTO(employee) : mapper.toBasicDTO(employee))
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: %s".formatted(employeeId))));
    }

    /**
     * Updates an existing employee record.
     *
     * @param employeeId        The ID of the employee to update.
     * @param employeeRequestDTO The request DTO containing updated employee details.
     * @return ResponseEntity with the updated employee details.
     */
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> updateEmployee(UUID employeeId, EmployeeRequestDTO employeeRequestDTO) {
        if(!isManager() && !isCurrentEmployee(employeeId))
            return ApiResponseDto.badRequest("Employee is not manager or is not current employee");

        Employee original = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("No Employee exists with ID: %s".formatted(employeeId)));
        original.setNameFirst(employeeRequestDTO.nameFirst());
        original.setNameLast(employeeRequestDTO.nameLast());
        original.setPhone(employeeRequestDTO.phone());
        original.setEmail(employeeRequestDTO.email());
        original.setDateOfBirth(employeeRequestDTO.dateOfBirth());

        if(employeeRequestDTO.userId() != null) {
            User user = userRepository.findById(employeeRequestDTO.userId())
                    .orElseThrow(() -> new EntityNotFoundException("Could not create employee. User with ID: %s Not Found."
                                    .formatted(employeeRequestDTO.userId())));

            original.setUser(user);
        }
        return ApiResponseDto.ok(isManager()
                ?
                mapper.toDetailDTO(employeeRepository.save(original))
                :
                mapper.toBasicDTO(employeeRepository.save(original)));
    }

    /**
     * Deletes an employee record.
     *
     * @param employeeId The ID of the employee to delete.
     * @return ResponseEntity with no content.
     */
    public ResponseEntity<ApiResponseDto<Void>> deleteEmployee(UUID employeeId) {
        Employee toDelete = employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Employee not found: %s".formatted(employeeId)));
        toDelete.setActive(false);
        employeeRepository.save(toDelete);
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

    /**
     * Checks if the current employee is the same as the one being updated.
     *
     * @param employeeId The ID of the employee to check.
     * @return true if the current employee is the same, false otherwise.
     */
    private boolean isCurrentEmployee(UUID employeeId) {
        CustomUserPrincipal currentUser = (CustomUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee currentEmployee = currentUser.getUser().getEmployee();
        return currentEmployee != null && currentUser.getUser().getEmployee().getId().equals(employeeId);
    }
}
