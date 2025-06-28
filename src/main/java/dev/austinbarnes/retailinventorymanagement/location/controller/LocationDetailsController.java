package dev.austinbarnes.retailinventorymanagement.location.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.location.dto.details.*;
import dev.austinbarnes.retailinventorymanagement.location.service.LocationDetailsService;
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
 * Controller for managing location details.
 * Provides endpoints to create, retrieve, update, and delete location details.
 */
@RestController
@RequestMapping("/api/v1/locations/details")
@RequiredArgsConstructor
@Slf4j
public class LocationDetailsController {
    private final LocationDetailsService service;

    /**
     * Creates a new location details entry.
     *
     * @param request the location details request DTO containing the details of the location to create.
     * @return ResponseEntity with the created location details.
     */
    @Operation(
            summary = "Create New Location Details",
            description = "Creates a new location details entry with the provided details."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Location details created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {LocationDetailsResponseBasicDTO.class, LocationDetailsResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or location details already exists")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<LocationDetailsResponseDTO>> createLocationDetails(
            @RequestBody @Valid LocationDetailsRequestDTO request) {
        log.info("Creating new location details: {}", request);
        return service.createLocationDetails(request);
    }

    /**
     * Retrieves location details by its ID.
     *
     * @param id the ID of the location details to retrieve.
     * @return ResponseEntity with the location details.
     */
    @Operation(
            summary = "Get Location Details by ID",
            description = "Retrieves location details by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Location details retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {LocationDetailsResponseBasicDTO.class, LocationDetailsResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Location details not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<LocationDetailsResponseDTO>> getLocationDetails(@PathVariable UUID id) {
        log.info("Retrieving location details with ID: {}", id);
        return service.getLocationDetails(id);
    }

    /**
     * Retrieves all location details.
     *
     * @return ResponseEntity with a list of all location details.
     */
    @Operation(
            summary = "Get All Location Details",
            description = "Retrieves all location details with optional filtering and pagination."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Location details retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {LocationDetailsResponseBasicDTO.class, LocationDetailsResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<LocationDetailsResponseDTO>>> getAllLocationDetails(
            @ModelAttribute @Valid LocationDetailsFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        log.info("Retrieving all location details");
        Sort sort = (sortBy != null && sortDirection != null) ?
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy) : Sort.unsorted();

        Pageable pageable = (page != null && size != null) ?
                PageRequest.of(page, size, sort) : Pageable.unpaged();
        return service.getAllLocationDetails(filterDTO, pageable);
    }

    /**
     * Updates an existing location details entry.
     *
     * @param id      the ID of the location details to update.
     * @param request LocationDetailsRequestDTO with the updated details.
     * @return ResponseEntity with the updated location details.
     */
    @Operation(
            summary = "Update Location Details",
            description = "Updates an existing location details entry with the provided details."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Location details updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {LocationDetailsResponseBasicDTO.class, LocationDetailsResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or location details not found"),
            @ApiResponse(responseCode = "404", description = "Location details not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<LocationDetailsResponseDTO>> updateLocationDetails(
            @PathVariable UUID id, @RequestBody @Valid LocationDetailsRequestDTO request) {
        log.info("Updating location details with ID: {}", id);
        return service.updateLocationDetails(id, request);
    }

    /**
     * Deletes a location details entry.
     *
     * @param id the ID of the location details to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Operation(
            summary = "Delete Location Details",
            description = "Deletes a location details entry by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Location details deleted successfully"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteLocationDetails(@PathVariable UUID id) {
        log.info("Deleting location details with ID: {}", id);
        return service.deleteLocationDetails(id);
    }
}
