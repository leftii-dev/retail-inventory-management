package dev.austinbarnes.retailinventorymanagement.location.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.location.dto.details.LocationDetailsRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.details.LocationDetailsResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.service.LocationDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<LocationDetailsResponseDTO>>> getAllLocationDetails() {
        log.info("Retrieving all location details");
        return service.getAllLocationDetails();
    }

    /**
     * Updates an existing location details entry.
     * @param id the ID of the location details to update.
     * @param request LocationDetailsRequestDTO with the updated details.
     *
     * @return ResponseEntity with the updated location details.
     */
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
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteLocationDetails(@PathVariable UUID id) {
        log.info("Deleting location details with ID: {}", id);
        return service.deleteLocationDetails(id);
    }
}
