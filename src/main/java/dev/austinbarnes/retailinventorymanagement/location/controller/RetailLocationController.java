package dev.austinbarnes.retailinventorymanagement.location.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationFilterDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.service.RetailLocationService;
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
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<RetailLocationResponseDTO>>> getAllRetailLocations(
            @ModelAttribute RetailLocationFilterDTO filterDTO,
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
     * @param id the ID of the retail location to update.
     * @param request the retail location request DTO containing the updated details.
     * @return ResponseEntity with the updated retail location details.
     */
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
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteRetailLocation(@PathVariable UUID id) {
        log.info("Deleting retail location with ID: {}", id);
        return service.deleteRetailLocation(id);
    }
}
