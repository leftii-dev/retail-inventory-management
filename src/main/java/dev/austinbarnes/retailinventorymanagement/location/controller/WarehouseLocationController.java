package dev.austinbarnes.retailinventorymanagement.location.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.service.WarehouseLocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<WarehouseLocationResponseDTO>>> getAllWarehouseLocations() {
        log.info("Retrieving all warehouse locations");
        return service.getAllWarehouseLocations();
    }

    /**
     * Updates an existing warehouse location.
     *
     * @param id      the ID of the warehouse location to update.
     * @param request the warehouse location request DTO containing the updated details.
     * @return ResponseEntity with the updated warehouse location details.
     */
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
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteWarehouseLocation(@PathVariable UUID id) {
        log.info("Deleting warehouse location with ID: {}", id);
        return service.deleteWarehouseLocation(id);
    }
}
