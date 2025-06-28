package dev.austinbarnes.retailinventorymanagement.inventory.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.*;
import dev.austinbarnes.retailinventorymanagement.inventory.service.VendorService;
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
@RequestMapping("/api/v1/vendors")
@RequiredArgsConstructor
@Slf4j
public class VendorController {
    private final VendorService service;

    /**
     * Creates a new vendor.
     *
     * @param request the vendor request DTO containing the details of the vendor to create.
     * @return ResponseEntity with the created vendor details.
     */
    @Operation(
            summary = "Create New Vendor",
            description = "Creates a new vendor with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Vendor created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {VendorResponseBasicDTO.class, VendorResponseDetailDTO.class}
                            )
                    )
            ),

    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<VendorResponseDTO>> createVendor(@RequestBody @Valid VendorRequestDTO request) {
        log.info("Creating vendor with request: {}", request);
        return service.createVendor(request);
    }

    /**
     * Retrieves a vendor by its ID.
     *
     * @param id the ID of the vendor to retrieve.
     * @return ResponseEntity with the vendor details.
     */
    @Operation(
            summary = "Get Vendor by ID",
            description = "Retrieves a vendor by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Vendor retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {VendorResponseBasicDTO.class, VendorResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Vendor not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<VendorResponseDTO>> getVendorById(@PathVariable UUID id) {
        log.info("Retrieving vendor with ID: {}", id);
        return service.getVendorById(id);
    }

    /**
     * Retrieves all vendors.
     *
     * @return ResponseEntity with a list of all vendors.
     */
    @Operation(
            summary = "Get All Vendors",
            description = "Retrieves all vendors with optional filtering, pagination, and sorting."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Vendors retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {VendorResponseBasicDTO.class, VendorResponseDetailDTO.class}
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<VendorResponseDTO>>> getAllVendors(
            @ModelAttribute @Valid VendorFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        log.info("Retrieving all vendors");
        Sort sort = (sortBy != null && sortDirection != null) ?
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy) :
                Sort.unsorted();
        Pageable pageable = (page != null && size != null) ?
                PageRequest.of(page, size, sort) :
                Pageable.unpaged();
        return service.getAllVendors(filterDTO, pageable);
    }

    /**
     * Updates an existing vendor.
     *
     * @param id the ID of the vendor to update.
     * @param request the vendor request DTO containing the updated details of the vendor.
     * @return ResponseEntity with the updated vendor details.
     */
    @Operation(
            summary = "Update Vendor",
            description = "Updates an existing vendor by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Vendor updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {VendorResponseBasicDTO.class, VendorResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Vendor not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<VendorResponseDTO>> updateVendor(@PathVariable UUID id, @Valid @RequestBody VendorRequestDTO request) {
        log.info("Updating vendor with ID: {} and request: {}", id, request);
        return service.updateVendor(id, request);
    }

    /**
     * Deletes a vendor by its ID.
     *
     * @param id the ID of the vendor to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Operation(
            summary = "Delete Vendor",
            description = "Deletes a vendor by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Vendor deleted successfully"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteVendor(@PathVariable UUID id) {
        log.info("Deleting vendor with ID: {}", id);
        return service.deleteVendor(id);
    }
}
