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
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper mapper;

    public ResponseEntity<ApiResponseDto<PermissionResponseDTO>> createPermission(PermissionRequestDTO permissionRequestDTO) {
        return ApiResponseDto.created(mapper.toDetailDTO(permissionRepository.save(mapper.toEntity(permissionRequestDTO))));
    }

    public ResponseEntity<ApiResponseDto<PermissionResponseDTO>> updatePermission(UUID permissionId, PermissionRequestDTO request) {
        Permission targetPermission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: %s".formatted(permissionId)));
        targetPermission.setName(request.name());
        targetPermission.setDescription(request.description());
        return ApiResponseDto.ok(mapper.toDetailDTO(permissionRepository.save(targetPermission)));
    }

    public ResponseEntity<ApiResponseDto<PermissionResponseDTO>> getPermissionById(UUID permissionId) {
        return ApiResponseDto.ok(permissionRepository.findById(permissionId).map(mapper::toDetailDTO)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: %s".formatted(permissionId))));
    }

    public ResponseEntity<ApiResponseDto<List<PermissionResponseDTO>>> getAllPermissions() {
        return ApiResponseDto.ok(permissionRepository.findAll()
                .stream()
                .map(permission -> (PermissionResponseDTO) mapper.toDetailDTO(permission))
                .toList());
    }

    public ResponseEntity<ApiResponseDto<Void>> deletePermission(UUID permissionId) {
        permissionRepository.deleteById(permissionId);
        return ApiResponseDto.noContent();
    }
}
