package dev.austinbarnes.retailinventorymanagement.location.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.location.dto.hours.LocationHoursRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.hours.LocationHoursResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.service.LocationHoursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<LocationHoursResponseDTO>>> getAllLocationHours() {
        log.info("Retrieving all location hours");
        return service.getAllLocationHours();
    }

    /**
     * Updates location hours by ID.
     *
     * @param id      the ID of the location hours to update.
     * @param request the location hours request DTO containing the updated details.
     * @return ResponseEntity with the updated location hours details.
     */
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
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteLocationHours(@PathVariable UUID id) {
        log.info("Deleting location hours with ID: {}", id);
        return service.deleteLocationHours(id);
    }
}
