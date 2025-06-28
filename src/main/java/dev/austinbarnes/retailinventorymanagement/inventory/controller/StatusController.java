package dev.austinbarnes.retailinventorymanagement.inventory.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.*;
import dev.austinbarnes.retailinventorymanagement.inventory.service.StatusService;
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

@RestController
@RequestMapping("/api/v1/status")
@RequiredArgsConstructor
@Slf4j
public class StatusController {
    private final StatusService statusService;

    /**
     * Creates a new status.
     *
     * @param request the status request DTO containing the details of the status to create.
     * @return ResponseEntity with the created status details.
     */
    @Operation(
            summary = "Create New Status",
            description = "Creates a new status with the provided details.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Status created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {StatusResponseBasicDTO.class, StatusResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or status already exists"),
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<StatusResponseDTO>> createStatus(@RequestBody @Valid StatusRequestDTO request) {
        log.info("Creating new status: {}", request);
        return statusService.createStatus(request);
    }

    /**
     * Retrieves a status by its ID.
     *
     * @param id the ID of the status to retrieve.
     * @return ResponseEntity with the status details.
     */
    @Operation(
            summary = "Get Status by ID",
            description = "Retrieves a status by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Status retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {StatusResponseBasicDTO.class, StatusResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Status not found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<StatusResponseDTO>> getStatusById(@PathVariable UUID id) {
        log.info("Retrieving status with ID: {}", id);
        return statusService.getStatusById(id);
    }

    /**
     * Retrieves all statuses.
     *
     * @return ResponseEntity with a list of all statuses.
     */
    @Operation(
            summary = "Get All Statuses",
            description = "Retrieves all statuses with optional filtering, pagination, and sorting."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Statuses retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {StatusResponseBasicDTO.class, StatusResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<StatusResponseDTO>>> getAllStatuses(
            @ModelAttribute @Valid StatusFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        log.info("Retrieving all statuses");
        Sort sort = (sortBy != null && sortDirection != null)
                ? Sort.by(Sort.Direction.fromString(sortDirection), sortBy)
                : Sort.unsorted();

        Pageable pageable = (page != null && size != null)
                ? PageRequest.of(page, size, sort)
                : Pageable.unpaged();
        return statusService.getAllStatuses(filterDTO, pageable);
    }

    /**
     * Updates an existing status.
     *
     * @param id      the ID of the status to update.
     * @param request the status request DTO containing the updated details of the status.
     * @return ResponseEntity with the updated status details.
     */
    @Operation(
            summary = "Update Status",
            description = "Updates an existing status with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Status updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {StatusResponseBasicDTO.class, StatusResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Status not found"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<StatusResponseDTO>> updateStatus(@PathVariable UUID id, @Valid @RequestBody StatusRequestDTO request) {
        log.info("Updating status with ID: {}", id);
        return statusService.updateStatus(id, request);
    }

    /**
     * Deletes a status by its ID.
     *
     * @param id the ID of the status to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Operation(
            summary = "Delete Status",
            description = "Deletes a status by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Status deleted successfully"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteStatus(@PathVariable UUID id) {
        log.info("Deleting status with ID: {}", id);
        return statusService.deleteStatus(id);
    }
}
