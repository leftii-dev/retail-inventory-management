package dev.austinbarnes.retailinventorymanagement.employee.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyFilterDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyResponseDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeeHierarchy;
import dev.austinbarnes.retailinventorymanagement.employee.mapper.EmployeeHierarchyMapper;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeHierarchyRepository;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeRepository;
import dev.austinbarnes.retailinventorymanagement.employee.specification.EmployeeHierarchySpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * EmployeeHierarchyService handles all operations related to employee hierarchy.
 * <p>
 * It provides methods to create, update, retrieve, and delete employee hierarchy relationships.
 */
@Service
@RequiredArgsConstructor
public class EmployeeHierarchyService {
    private final EmployeeHierarchyRepository employeeHierarchyRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeHierarchyMapper mapper;

    /**
     * Creates a new employee hierarchy relationship.
     *
     * @param employeeHierarchyRequestDTO The request DTO containing employee hierarchy details.
     * @return ResponseEntity with the created employee hierarchy details.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponseDto<EmployeeHierarchyResponseDTO>> createEmployeeHierarchy(
            EmployeeHierarchyRequestDTO employeeHierarchyRequestDTO
    ) {
        return ApiResponseDto.created(mapper.toDetailDTO(employeeHierarchyRepository.save(mapper.toEntity(employeeHierarchyRequestDTO))));
    }

    /**
     * Updates an existing employee hierarchy relationship.
     *
     * @param request The request DTO containing updated employee hierarchy details.
     * @return ResponseEntity with the updated employee hierarchy details.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponseDto<EmployeeHierarchyResponseDTO>> updateHierarchy(EmployeeHierarchyRequestDTO request) {
        EmployeeHierarchy original = employeeHierarchyRepository.findByEmployeeId(request.employeeID())
                .orElseThrow(() -> new EntityNotFoundException("Manager relationship for user ID: %s not found".formatted(request.employeeID())));

        original.setManager(employeeRepository.findById(request.managerID())
                .orElseThrow(() -> new EntityNotFoundException("Manager with ID: %s not found".formatted(request.managerID()))));

        return ApiResponseDto.ok(mapper.toDetailDTO(employeeHierarchyRepository.save(original)));
    }

    /**
     * Retrieves the employee hierarchy for a specific employee.
     *
     * @param employeeID The ID of the employee.
     * @return ResponseEntity with the employee hierarchy details.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<ApiResponseDto<EmployeeHierarchyResponseDTO>> getEmployeeHierarchy(UUID employeeID) {
        return ApiResponseDto.ok(employeeHierarchyRepository.findByEmployeeId(employeeID)
                .map(hierarchy -> isManager()
                        ? mapper.toDetailDTO(hierarchy)
                        : (EmployeeHierarchyResponseDTO) mapper.toBasicDTO(hierarchy))
                .orElseThrow(() -> new EntityNotFoundException("No Manager Relationship found for employee: %s".formatted(employeeID))));
    }

    /**
     * Retrieves all employee hierarchies.
     *
     * @return ResponseEntity with a list of all employee hierarchies.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<ApiResponseDto<List<EmployeeHierarchyResponseDTO>>> getEmployeeHierarchyAll(
            EmployeeHierarchyFilterDTO filterDTO,
            Pageable pageable) {
        Specification<EmployeeHierarchy> spec = EmployeeHierarchySpecifications.applyFilters(filterDTO);
        return ApiResponseDto.ok(employeeHierarchyRepository.findAll(spec, pageable).stream()
                .map(hierarchy -> isManager()
                        ? mapper.toDetailDTO(hierarchy)
                        : (EmployeeHierarchyResponseDTO) mapper.toBasicDTO(hierarchy))
                .toList());
    }

    /**
     * Deletes an employee hierarchy relationship by ID.
     *
     * @param relationshipId The ID of the employee hierarchy relationship to delete.
     * @return ResponseEntity with no content.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponseDto<Void>> deleteEmployeeHierarchyRelationship(UUID relationshipId) {
        employeeHierarchyRepository.deleteById(relationshipId);
        return ApiResponseDto.noContent();
    }

    /**
     * Checks if the current user has manager or admin role.
     *
     * @return true if the user is a manager or admin, false otherwise.
     */
    private boolean isManager() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals("ROLE_MANAGER") || authority.getAuthority().equals("ROLE_ADMIN")
        );
    }
}
