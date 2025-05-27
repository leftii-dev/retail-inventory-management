package dev.austinbarnes.retailinventorymanagement.location.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<LocationResponseDTO>>> getAllLocations() {
        log.info("Retrieving all locations");
        return service.getLocations();
    }

    /**
     * Updates an existing location.
     *
     * @param id      the ID of the location to update.
     * @param request the location request DTO containing the updated details of the location.
     * @return ResponseEntity with the updated location details.
     */
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
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteLocation(@PathVariable UUID id) {
        log.info("Deleting location with ID: {}", id);
        return service.deleteLocation(id);
    }
}
