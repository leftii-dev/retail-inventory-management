package dev.austinbarnes.retailinventorymanagement.location.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.*;
import dev.austinbarnes.retailinventorymanagement.location.service.RetailLocationService;
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
@RequestMapping("/api/v1/locations/retail")
@Slf4j
public class RetailLocationController {
    private final RetailLocationService service;

    /**
     * Creates a new retail location.
     *
     * @param request the retail location request DTO containing the details of the retail location to create.
     * @return ResponseEntity with the created retail location details.
     */
    @Operation(
            summary = "Create New Retail Location",
            description = "Creates a new retail location with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Retail location created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {RetailLocationResponseBasicDTO.class, RetailLocationResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or retail location already exists")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<RetailLocationResponseDTO>> createRetailLocation(
            RetailLocationRequestDTO request) {
        log.info("Creating new retail location: {}", request);
        return service.createRetailLocation(request);
    }

    /**
     * Retrieves a retail location by its ID.
     *
     * @param id the ID of the retail location to retrieve.
     * @return ResponseEntity with the retail location details.
     */
    @Operation(
            summary = "Get Retail Location by ID",
            description = "Retrieves a retail location by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Retail location retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {RetailLocationResponseBasicDTO.class, RetailLocationResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Retail location not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<RetailLocationResponseDTO>> getRetailLocation(@PathVariable UUID id) {
        log.info("Retrieving retail location with ID: {}", id);
        return service.getRetailLocation(id);
    }

    /**
     * Retrieves all retail locations.
     *
     * @return ResponseEntity with a list of all retail locations.
     */
    @Operation(
            summary = "Get All Retail Locations",
            description = "Retrieves all retail locations with optional filtering and pagination."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Retail locations retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {RetailLocationResponseBasicDTO.class, RetailLocationResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<RetailLocationResponseDTO>>> getAllRetailLocations(
            @ModelAttribute @Valid RetailLocationFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        log.info("Retrieving all retail locations");
        Sort sort = (sortBy != null && sortDirection != null) ?
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy) : Sort.unsorted();

        Pageable pageable = (page != null && size != null) ?
                PageRequest.of(page, size, sort) : Pageable.unpaged();
        return service.getAllRetailLocations(filterDTO, pageable);
    }

    /**
     * Updates an existing retail location.
     *
     * @param id      the ID of the retail location to update.
     * @param request the retail location request DTO containing the updated details.
     * @return ResponseEntity with the updated retail location details.
     */
    @Operation(
            summary = "Update Retail Location",
            description = "Updates an existing retail location by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Retail location updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {RetailLocationResponseBasicDTO.class, RetailLocationResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Retail location not found"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<RetailLocationResponseDTO>> updateRetailLocation(@PathVariable UUID id,
                                                                                          @RequestBody @Valid RetailLocationRequestDTO request) {
        log.info("Updating retail location with ID: {}", id);
        return service.updateRetailLocation(id, request);
    }

    /**
     * Deletes a retail location by its ID.
     *
     * @param id the ID of the retail location to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Operation(
            summary = "Delete Retail Location",
            description = "Deletes a retail location by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Retail location deleted successfully"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteRetailLocation(@PathVariable UUID id) {
        log.info("Deleting retail location with ID: {}", id);
        return service.deleteRetailLocation(id);
    }
}
