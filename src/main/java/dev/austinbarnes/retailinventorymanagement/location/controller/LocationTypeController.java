package dev.austinbarnes.retailinventorymanagement.location.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.service.LocationTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<LocationTypeResponseDTO>>> getAllLocationTypes() {
        log.info("Retrieving all location types");
        return service.getAllLocationTypes();
    }

    /**
     * Updates an existing location type.
     * @param id the ID of the location type to update.
     * @param request the location type request DTO containing the updated details of the location type.
     * @return ResponseEntity with the updated location type details.
     */
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
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteLocationType(@PathVariable UUID id) {
        log.info("Deleting location type with ID: {}", id);
        return service.deleteLocationType(id);
    }
}
