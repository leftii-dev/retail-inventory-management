package dev.austinbarnes.retailinventorymanagement.employee.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.PermissionRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.PermissionResponseDTO;
import dev.austinbarnes.retailinventorymanagement.employee.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/permissions")
@Slf4j
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping("/")
    public ResponseEntity<ApiResponseDto<PermissionResponseDTO>> createPermission(@RequestBody @Valid PermissionRequestDTO request) {
        log.info("Create permission request: {}", request);
        return permissionService.createPermission(request);
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<ApiResponseDto<PermissionResponseDTO>> getPermissionById(@PathVariable UUID permissionId) {
        log.info("Get permission by ID: {}", permissionId);
        return permissionService.getPermissionById(permissionId);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponseDto<List<PermissionResponseDTO>>> getAllPermissions() {
        log.info("Get all permissions");
        return permissionService.getAllPermissions();
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<ApiResponseDto<PermissionResponseDTO>> updatePermission(
            @PathVariable UUID permissionId,
            @RequestBody @Valid PermissionRequestDTO request) {
        log.info("Update permission request: {}", request);
        return permissionService.updatePermission(permissionId, request);
    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<ApiResponseDto<Void>> deletePermission(@PathVariable UUID permissionId) {
        log.info("Delete permission with ID: {}", permissionId);
        return permissionService.deletePermission(permissionId);
    }
}
