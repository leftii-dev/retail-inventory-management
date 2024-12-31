package dev.austinbarnes.retailinventorymanagement.employee.service;

import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.auth.repo.UserRepository;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyResponseDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.employee.mapper.EmployeeMapper;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeRepository;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;
import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeHierarchyService employeeHierarchyService;
    private final EmployeeMapper mapper;
    private final UserRepository userRepository;


    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> createEmployee(EmployeeRequestDTO employeeRequestDTO) {
        // Check if user already assigned to Employee object, return bad request, if so
        if (employeeRepository.findByUserId(employeeRequestDTO.userId()).isPresent()) {
            return ApiResponseDto.badRequest("Employee already exists for this User: %s".formatted(employeeRequestDTO.userId()));
        }

        //Create new Employee object return detail DTO as endpoint is only reachable by manager/admin
        return ApiResponseDto.created(mapper.toDetailDTO(employeeRepository.save(mapper.toEntity(employeeRequestDTO))));
    }

    public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> createEmployeeWithManager(EmployeeRequestDTO employeeRequestDTO, UUID managerID) {
        // Check if Entity exists already
        if (employeeRepository.findByUserId(employeeRequestDTO.userId()).isPresent()) {
            return ApiResponseDto.badRequest("Employee already exists for this User: %s".formatted(employeeRequestDTO.userId()));
        }

        EmployeeResponseDTO responseDTO = mapper.toDetailDTO(employeeRepository.save(mapper.toEntity(employeeRequestDTO)));
        EmployeeHierarchyRequestDTO hierarchyRequestDTO = new EmployeeHierarchyRequestDTO(responseDTO.id(), managerID);
        employeeHierarchyService.createEmployeeHierarchy(hierarchyRequestDTO);

        return ApiResponseDto.created(responseDTO);
    }

    public ResponseEntity<ApiResponseDto<List<EmployeeResponseDTO>>> getAllEmployees() {
        return ApiResponseDto.ok(employeeRepository.findAll().stream()
                .map(employee -> isManager()
                        ? (EmployeeResponseDTO) mapper.toDetailDTO(employee)
                        : (EmployeeResponseDTO) mapper.toBasicDTO(employee))
                .toList());
    }

    public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> getEmployeeById(UUID employeeId) {
        return ApiResponseDto.ok(employeeRepository.findById(employeeId).map(employee -> isManager() ? mapper.toDetailDTO(employee) : mapper.toBasicDTO(employee))
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: %s".formatted(employeeId))));
    }

    public ResponseEntity<ApiResponseDto<EmployeeResponseDTO>> updateEmployee(UUID employeeId, EmployeeRequestDTO employeeRequestDTO) {
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

    public ResponseEntity<ApiResponseDto<Void>> deleteEmployee(UUID employeeId) {
        employeeRepository.deleteById(employeeId);
        return ApiResponseDto.noContent();
    }

    private boolean isManager() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals("ROLE_MANAGER") || authority.getAuthority().equals("ROLE_ADMIN")
        );
    }
}
