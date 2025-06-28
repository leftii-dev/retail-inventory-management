package dev.austinbarnes.retailinventorymanagement.location.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationFilterDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.service.WarehouseLocationService;
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
 * Controller for managing warehouse locations.
 * Provides endpoints to create, retrieve, update, and delete warehouse locations.
 */
@RestController
@RequestMapping("/api/v1/locations/warehouse")
@RequiredArgsConstructor
@Slf4j
public class WarehouseLocationController {
    private final WarehouseLocationService service;

    /**
     * Creates a new warehouse location.
     *
     * @param request the warehouse location request DTO containing the details of the warehouse location to create.
     * @return ResponseEntity with the created warehouse location details.
     */
    @Operation(
            summary = "Create New Warehouse Location",
            description = "Creates a new warehouse location with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Warehouse location created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {WarehouseLocationResponseBasicDTO.class, WarehouseLocationResponseDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or warehouse location already exists")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<WarehouseLocationResponseDTO>> createWarehouseLocation(
            @RequestBody @Valid WarehouseLocationRequestDTO request) {
        log.info("Creating new warehouse location: {}", request);
        return service.createWarehouseLocation(request);
    }

    /**
     * Retrieves a warehouse location by its ID.
     *
     * @param id the ID of the warehouse location to retrieve.
     * @return ResponseEntity with the warehouse location details.
     */
    @Operation(
            summary = "Get Warehouse Location by ID",
            description = "Retrieves a warehouse location by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Warehouse location retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {WarehouseLocationResponseBasicDTO.class, WarehouseLocationResponseDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Warehouse location not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<WarehouseLocationResponseDTO>> getWarehouseLocation(@PathVariable UUID id) {
        log.info("Retrieving warehouse location with ID: {}", id);
        return service.getWarehouseLocation(id);
    }

    /**
     * Retrieves all warehouse locations.
     *
     * @return ResponseEntity with a list of all warehouse locations.
     */
    @Operation(
            summary = "Get All Warehouse Locations",
            description = "Retrieves all warehouse locations with optional filtering and pagination."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Warehouse locations retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {WarehouseLocationResponseBasicDTO.class, WarehouseLocationResponseDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid filter parameters")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<WarehouseLocationResponseDTO>>> getAllWarehouseLocations(
            @ModelAttribute @Valid WarehouseLocationFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        log.info("Retrieving all warehouse locations");
        Sort sort = (sortBy != null && sortDirection != null) ?
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy) :
                Sort.unsorted();
        Pageable pageable = (page != null && size != null) ?
                PageRequest.of(page, size, sort) :
                Pageable.unpaged();
        return service.getAllWarehouseLocations(filterDTO, pageable);
    }

    /**
     * Updates an existing warehouse location.
     *
     * @param id      the ID of the warehouse location to update.
     * @param request the warehouse location request DTO containing the updated details.
     * @return ResponseEntity with the updated warehouse location details.
     */
    @Operation(
            summary = "Update Warehouse Location",
            description = "Updates an existing warehouse location with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Warehouse location updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {WarehouseLocationResponseBasicDTO.class, WarehouseLocationResponseDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or warehouse location not found"),
            @ApiResponse(responseCode = "404", description = "Warehouse location not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<WarehouseLocationResponseDTO>> updateWarehouseLocation(
            @PathVariable UUID id, @RequestBody @Valid WarehouseLocationRequestDTO request) {
        log.info("Updating warehouse location with ID: {}", id);
        return service.updateWarehouseLocation(id, request);
    }

    /**
     * Deletes a warehouse location by its ID.
     *
     * @param id the ID of the warehouse location to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Operation(
            summary = "Delete Warehouse Location",
            description = "Deletes a warehouse location by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Warehouse location deleted successfully"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteWarehouseLocation(@PathVariable UUID id) {
        log.info("Deleting warehouse location with ID: {}", id);
        return service.deleteWarehouseLocation(id);
    }
}
