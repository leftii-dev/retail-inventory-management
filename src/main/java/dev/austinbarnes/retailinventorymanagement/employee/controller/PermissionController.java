package dev.austinbarnes.retailinventorymanagement.employee.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.*;
import dev.austinbarnes.retailinventorymanagement.employee.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
 * PermissionController handles all permission-related operations.
 * <p>
 * It provides endpoints to create, update, delete, and retrieve permissions.
 */
@RestController
@RequestMapping("api/v1/permissions")
@Slf4j
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    /**
     * Creates a new permission.
     *
     * @param request The permission request DTO containing permission details.
     * @return ResponseEntity with the created permission details.
     */
    @Operation(
            summary = "Create Permission",
            description = "Creates a new permission."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Permission created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {PermissionResponseBasicDTO.class, PermissionResponseDetailDTO.class})
                    )
            ),
    })
    @PostMapping("/")
    public ResponseEntity<ApiResponseDto<PermissionResponseDTO>> createPermission(@RequestBody @Valid PermissionRequestDTO request) {
        log.info("Create permission request: {}", request);
        return permissionService.createPermission(request);
    }

    /**
     * Retrieves a permission by its ID.
     *
     * @param permissionId The ID of the permission to retrieve.
     * @return ResponseEntity with the permission details.
     */
    @Operation(
            summary = "Get Permission by ID",
            description = "Retrieves a permission by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Permission retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {PermissionResponseBasicDTO.class, PermissionResponseDetailDTO.class}))),
            @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    @GetMapping("/{permissionId}")
    public ResponseEntity<ApiResponseDto<PermissionResponseDTO>> getPermissionById(@PathVariable UUID permissionId) {
        log.info("Get permission by ID: {}", permissionId);
        return permissionService.getPermissionById(permissionId);
    }

    /**
     * Retrieves all permissions.
     *
     * @return ResponseEntity with a list of all permissions.
     */
    @Operation(
            summary = "Get All Permissions",
            description = "Retrieves all permissions with optional filtering and pagination."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Permissions retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {PermissionResponseBasicDTO.class, PermissionResponseDetailDTO.class}))),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
    @GetMapping("/")
    public ResponseEntity<ApiResponseDto<List<PermissionResponseDTO>>> getAllPermissions(
            @ModelAttribute @Valid PermissionFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
            ) {
        log.info("Get all permissions");
        Sort sort = (sortBy != null && sortDirection != null
                ? Sort.by(Sort.Direction.fromString(sortDirection), sortBy)
                : Sort.unsorted());

        Pageable pageable = (page != null && size != null
                ? PageRequest.of(page, size, sort)
                : Pageable.unpaged());
        return permissionService.getAllPermissions(filterDTO, pageable);
    }

    /**
     * Updates an existing permission.
     *
     * @param permissionId The ID of the permission to update.
     * @param request      The DTO containing the updated details of the permission.
     * @return ResponseEntity with the updated permission details.
     */
    @Operation(
            summary = "Update Permission",
            description = "Updates an existing permission.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Permission updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {PermissionResponseBasicDTO.class, PermissionResponseDetailDTO.class}))),
            @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    @PutMapping("/{permissionId}")
    public ResponseEntity<ApiResponseDto<PermissionResponseDTO>> updatePermission(
            @PathVariable UUID permissionId,
            @RequestBody @Valid PermissionRequestDTO request) {
        log.info("Update permission request: {}", request);
        return permissionService.updatePermission(permissionId, request);
    }

    /**
     * Deletes a permission by its ID.
     *
     * @param permissionId The ID of the permission to delete.
     * @return ResponseEntity indicating the result of the deletion.
     */
    @Operation(
            summary = "Delete Permission",
            description = "Deletes a permission by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Permission deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {Void.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    @DeleteMapping("/{permissionId}")
    public ResponseEntity<ApiResponseDto<Void>> deletePermission(@PathVariable UUID permissionId) {
        log.info("Delete permission with ID: {}", permissionId);
        return permissionService.deletePermission(permissionId);
    }
}
