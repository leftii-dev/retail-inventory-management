package dev.austinbarnes.retailinventorymanagement.location.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.location.dto.type.*;
import dev.austinbarnes.retailinventorymanagement.location.service.LocationTypeService;
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
@RequestMapping("/api/v1/location/types")
@RequiredArgsConstructor
@Slf4j
public class LocationTypeController {
    private final LocationTypeService service;

    /**
     * Creates a new location type.
     *
     * @param request the location type request DTO containing the details of the location type to create.
     * @return ResponseEntity with the created location type details.
     */
    @Operation(
            summary = "Create New Location Type",
            description = "Creates a new location type with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Location type created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {LocationTypeResponseBasicDTO.class, LocationTypeResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or location type already exists")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<LocationTypeResponseDTO>> createLocationType(
            LocationTypeRequestDTO request) {
        log.info("Creating location type: {}", request);
        return service.createLocationType(request);
    }

    /**
     * Retrieves a location type by its ID.
     *
     * @param id the ID of the location type to retrieve.
     * @return ResponseEntity with the location type details.
     */
    @Operation(
            summary = "Get Location Type by ID",
            description = "Retrieves a location type by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Location type retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {LocationTypeResponseBasicDTO.class, LocationTypeResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Location type not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<LocationTypeResponseDTO>> getLocationType(@PathVariable UUID id) {
        log.info("Retrieving location type with ID: {}", id);
        return service.getLocationType(id);
    }

    /**
     * Retrieves all location types.
     *
     * @return ResponseEntity with a list of all location types.
     */
    @Operation(
            summary = "Get All Location Types",
            description = "Retrieves all location types with optional filtering and pagination."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Location types retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {LocationTypeResponseBasicDTO.class, LocationTypeResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<LocationTypeResponseDTO>>> getAllLocationTypes(
            @ModelAttribute @Valid LocationTypeFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        log.info("Retrieving all location types");
        Sort sort = (sortBy != null && sortDirection != null) ?
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy) : Sort.unsorted();
        Pageable pageable = (page != null && size != null) ?
                PageRequest.of(page, size, sort) : Pageable.unpaged();
        return service.getAllLocationTypes(filterDTO, pageable);
    }

    /**
     * Updates an existing location type.
     *
     * @param id      the ID of the location type to update.
     * @param request the location type request DTO containing the updated details of the location type.
     * @return ResponseEntity with the updated location type details.
     */
    @Operation(
            summary = "Update Location Type",
            description = "Updates an existing location type by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Location type updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {LocationTypeResponseBasicDTO.class, LocationTypeResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Location type not found"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<LocationTypeResponseDTO>> updateLocationType(
            @PathVariable UUID id, @RequestBody LocationTypeRequestDTO request) {
        log.info("Updating location type with ID: {}", id);
        return service.updateLocationType(id, request);
    }

    /**
     * Deletes a location type by its ID.
     *
     * @param id the ID of the location type to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Operation(
            summary = "Delete Location Type",
            description = "Deletes a location type by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Location type deleted successfully"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteLocationType(@PathVariable UUID id) {
        log.info("Deleting location type with ID: {}", id);
        return service.deleteLocationType(id);
    }
}
