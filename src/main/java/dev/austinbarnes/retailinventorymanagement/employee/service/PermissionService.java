package dev.austinbarnes.retailinventorymanagement.employee.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.PermissionRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.PermissionResponseDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Permission;
import dev.austinbarnes.retailinventorymanagement.employee.mapper.PermissionMapper;
import dev.austinbarnes.retailinventorymanagement.employee.repo.PermissionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
/**
 * Service class for managing permissions.
 * <p>
 * This class provides methods to create, update, retrieve, and delete permissions.
 * It uses the {@link PermissionRepository} to interact with the database and
 * {@link PermissionMapper} to map between DTOs and entity objects.
 */
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper mapper;

    /**
     * Creates a new permission.
     *
     * @param permissionRequestDTO the DTO containing the details of the permission to create
     * @return a ResponseEntity containing the created permission details
     */
    public ResponseEntity<ApiResponseDto<PermissionResponseDTO>> createPermission(PermissionRequestDTO permissionRequestDTO) {
        return ApiResponseDto.created(mapper.toDetailDTO(permissionRepository.save(mapper.toEntity(permissionRequestDTO))));
    }

    /**
     * Updates an existing permission.
     *
     * @param permissionId the ID of the permission to update
     * @param request      the DTO containing the updated details of the permission
     * @return a ResponseEntity containing the updated permission details
     */
    public ResponseEntity<ApiResponseDto<PermissionResponseDTO>> updatePermission(UUID permissionId, PermissionRequestDTO request) {
        Permission targetPermission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: %s".formatted(permissionId)));
        targetPermission.setName(request.name());
        targetPermission.setDescription(request.description());
        return ApiResponseDto.ok(mapper.toDetailDTO(permissionRepository.save(targetPermission)));
    }

    /**
     * Retrieves a permission by its ID.
     *
     * @param permissionId the ID of the permission to retrieve
     * @return a ResponseEntity containing the permission details
     */
    public ResponseEntity<ApiResponseDto<PermissionResponseDTO>> getPermissionById(UUID permissionId) {
        return ApiResponseDto.ok(permissionRepository.findById(permissionId).map(mapper::toDetailDTO)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: %s".formatted(permissionId))));
    }

    /**
     * Retrieves all permissions.
     *
     * @return a ResponseEntity containing a list of all permissions
     */
    public ResponseEntity<ApiResponseDto<List<PermissionResponseDTO>>> getAllPermissions() {
        return ApiResponseDto.ok(permissionRepository.findAll()
                .stream()
                .map(permission -> (PermissionResponseDTO) mapper.toDetailDTO(permission))
                .toList());
    }

    /**
     * Deletes a permission by its ID.
     *
     * @param permissionId the ID of the permission to delete
     * @return a ResponseEntity indicating the result of the deletion
     */
    public ResponseEntity<ApiResponseDto<Void>> deletePermission(UUID permissionId) {
        permissionRepository.deleteById(permissionId);
        return ApiResponseDto.noContent();
    }
}
