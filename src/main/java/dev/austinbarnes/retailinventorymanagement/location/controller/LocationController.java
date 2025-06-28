package dev.austinbarnes.retailinventorymanagement.location.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.location.dto.*;
import dev.austinbarnes.retailinventorymanagement.location.service.LocationService;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/locations")
@Slf4j
public class LocationController {
    private final LocationService service;

    /**
     * Creates a new location.
     *
     * @param request the location request DTO containing the details of the location to create.
     * @return ResponseEntity with the created location details.
     */
    @Operation(
            summary = "Create New Location",
            description = "Creates a new location with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Location created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {LocationResponseBasicDTO.class, LocationResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or location already exists")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<LocationResponseDTO>> createLocation(@RequestBody @Valid LocationRequestDTO request) {
        log.info("Creating new location: {}", request);
        return service.createLocation(request);
    }

    /**
     * Retrieves a location by its ID.
     *
     * @param id the ID of the location to retrieve.
     * @return ResponseEntity with the location details.
     */
    @Operation(
            summary = "Get Location by ID",
            description = "Retrieves a location by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Location retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {LocationResponseBasicDTO.class, LocationResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Location not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<LocationResponseDTO>> getLocationById(@PathVariable UUID id) {
        log.info("Retrieving location with ID: {}", id);
        return service.getLocationById(id);
    }

    /**
     * Retrieves all locations.
     *
     * @return ResponseEntity containing a list of all locations.
     */
    @Operation(
            summary = "Get All Locations",
            description = "Retrieves all locations with optional filtering, pagination, and sorting."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Locations retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {LocationResponseBasicDTO.class, LocationResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid filter parameters")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<LocationResponseDTO>>> getAllLocations(
            @ModelAttribute @Valid LocationFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        log.info("Retrieving all locations");
        Sort sort = (sortBy != null && sortDirection != null) ?
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy) : Sort.unsorted();
        Pageable pageable = (page != null && size != null) ? PageRequest.of(page, size, sort) : Pageable.unpaged();
        return service.getLocations(filterDTO, pageable);
    }

    /**
     * Updates an existing location.
     *
     * @param id      the ID of the location to update.
     * @param request the location request DTO containing the updated details of the location.
     * @return ResponseEntity with the updated location details.
     */
    @Operation(
            summary = "Update Location",
            description = "Updates an existing location with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Location updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {LocationResponseBasicDTO.class, LocationResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or location not found"),
            @ApiResponse(responseCode = "404", description = "Location not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<LocationResponseDTO>> updateLocation(@PathVariable UUID id, @RequestBody @Valid LocationRequestDTO request) {
        log.info("Updating location with ID: {}", id);
        return service.updateLocation(id, request);
    }

    /**
     * Deletes a location by its ID.
     *
     * @param id the ID of the location to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Operation(
            summary = "Delete Location",
            description = "Deletes a location by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Location deleted successfully"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteLocation(@PathVariable UUID id) {
        log.info("Deleting location with ID: {}", id);
        return service.deleteLocation(id);
    }
}
