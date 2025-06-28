package dev.austinbarnes.retailinventorymanagement.location.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.location.dto.hours.*;
import dev.austinbarnes.retailinventorymanagement.location.service.LocationHoursService;
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
@RequestMapping("/api/v1/locations/hours")
@RequiredArgsConstructor
@Slf4j
public class LocationHoursController {
    private final LocationHoursService service;

    /**
     * Creates new location hours.
     *
     * @param request the location hours request DTO containing the details of the hours to create.
     * @return ResponseEntity with the created location hours details.
     */
    @Operation(
            summary = "Create New Location Hours",
            description = "Creates new location hours with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Location hours created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {LocationHoursResponseBasicDTO.class, LocationHoursResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or location hours already exists")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<LocationHoursResponseDTO>> createLocationHours(
            @RequestBody @Valid LocationHoursRequestDTO request) {
        log.info("Creating location hours: {}", request);
        return service.createLocationHours(request);
    }

    /**
     * Retrieves location hours by ID.
     *
     * @param id the ID of the location hours to retrieve.
     * @return ResponseEntity with the location hours details.
     */
    @Operation(
            summary = "Get Location Hours by ID",
            description = "Retrieves location hours by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Location hours retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {LocationHoursResponseBasicDTO.class, LocationHoursResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Location hours not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<LocationHoursResponseDTO>> getLocationHours(@PathVariable UUID id){
        log.info("Retrieving location hours: {}", id);
        return service.getLocationHoursById(id);
    }

    /**
     * Retrieves all location hours.
     *
     * @return ResponseEntity with a list of all location hours.
     */
    @Operation(
            summary = "Get All Location Hours",
            description = "Retrieves all location hours with optional filtering and pagination."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Location hours retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {LocationHoursResponseBasicDTO.class, LocationHoursResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<LocationHoursResponseDTO>>> getAllLocationHours(
            @ModelAttribute @Valid LocationHoursFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
            ) {
        log.info("Retrieving all location hours");
        Sort sort = (sortBy != null && sortDirection != null) ?
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy) : Sort.unsorted();
        Pageable pageable = (page != null && size != null) ?
                PageRequest.of(page, size, sort): Pageable.unpaged();

        return service.getAllLocationHours(filterDTO, pageable);
    }

    /**
     * Updates location hours by ID.
     *
     * @param id      the ID of the location hours to update.
     * @param request the location hours request DTO containing the updated details.
     * @return ResponseEntity with the updated location hours details.
     */
    @Operation(
            summary = "Update Location Hours",
            description = "Updates existing location hours by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Location hours updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {LocationHoursResponseBasicDTO.class, LocationHoursResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Location hours not found"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<LocationHoursResponseDTO>> updateLocationHours(
            @PathVariable UUID id, @RequestBody @Valid LocationHoursRequestDTO request) {
        log.info("Updating location hours with ID: {}", id);
        return service.updateLocationHours(id, request);
    }

    /**
     * Deletes location hours by ID.
     *
     * @param id the ID of the location hours to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Operation(
            summary = "Delete Location Hours",
            description = "Deletes location hours by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Location hours deleted successfully"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteLocationHours(@PathVariable UUID id) {
        log.info("Deleting location hours with ID: {}", id);
        return service.deleteLocationHours(id);
    }
}
