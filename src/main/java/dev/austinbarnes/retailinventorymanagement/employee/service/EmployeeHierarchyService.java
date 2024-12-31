package dev.austinbarnes.retailinventorymanagement.employee.service;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyResponseDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeeHierarchy;
import dev.austinbarnes.retailinventorymanagement.employee.mapper.EmployeeHierarchyMapper;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeHierarchyRepository;
import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeHierarchyService {
    private final EmployeeHierarchyRepository employeeHierarchyRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeHierarchyMapper mapper;

    public ResponseEntity<ApiResponseDto<EmployeeHierarchyResponseDTO>> createEmployeeHierarchy(
            EmployeeHierarchyRequestDTO employeeHierarchyRequestDTO
    ) {
        return ApiResponseDto.created(isManager()
                ?
                mapper.toDetailDTO(employeeHierarchyRepository.save(mapper.toEntity(employeeHierarchyRequestDTO)))
                :
                mapper.toBasicDTO(employeeHierarchyRepository.save(mapper.toEntity(employeeHierarchyRequestDTO))));
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponseDto<EmployeeHierarchyResponseDTO>> updateHierarchy(EmployeeHierarchyRequestDTO request){
        EmployeeHierarchy original = employeeHierarchyRepository.findByEmployeeId(request.employeeID())
                .orElseThrow(() -> new EntityNotFoundException("Manager relationship for user ID: %s not found".formatted(request.employeeID())));

        original.setManager(employeeRepository.findById(request.managerID())
                .orElseThrow(() -> new EntityNotFoundException("Manager with ID: %s not found".formatted(request.managerID()))));

        return ApiResponseDto.ok(mapper.toDetailDTO(employeeHierarchyRepository.save(original)));
    }

    public ResponseEntity<ApiResponseDto<EmployeeHierarchyResponseDTO>> getEmployeeHierarchy(UUID employeeID) {
        return ApiResponseDto.ok(employeeHierarchyRepository.findByEmployeeId(employeeID)
                .map(hierarchy -> isManager()
                        ? (EmployeeHierarchyResponseDTO) mapper.toDetailDTO(hierarchy)
                        : (EmployeeHierarchyResponseDTO) mapper.toBasicDTO(hierarchy))
                .orElseThrow(() -> new EntityNotFoundException("No Manager Relationship found for employee: %s".formatted(employeeID))));
    }

    public ResponseEntity<ApiResponseDto<List<EmployeeHierarchyResponseDTO>>> getEmployeeHierarchyAll(){
        return ApiResponseDto.ok(employeeHierarchyRepository.findAll().stream()
                .map(hierarchy -> isManager()
                        ? (EmployeeHierarchyResponseDTO) mapper.toDetailDTO(hierarchy)
                        : (EmployeeHierarchyResponseDTO) mapper.toBasicDTO(hierarchy))
                .toList());
    }

    public ResponseEntity<ApiResponseDto<Void>> deleteEmployeeHierarchyRelationship(UUID employeeID){
        employeeHierarchyRepository.deleteById(employeeID);
        return ApiResponseDto.noContent();
    }

    private boolean isManager() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals("ROLE_MANAGER") || authority.getAuthority().equals("ROLE_ADMIN")
        );
    }
}
