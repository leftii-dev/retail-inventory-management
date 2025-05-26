package dev.austinbarnes.retailinventorymanagement.inventory.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.service.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @PostMapping
    public ResponseEntity<ApiResponseDto<VendorResponseDTO>> createVendor(VendorRequestDTO request) {
        log.info("Creating vendor with request: {}", request);
        return service.createVendor(request);
    }

    /**
     * Retrieves a vendor by its ID.
     *
     * @param id the ID of the vendor to retrieve.
     * @return ResponseEntity with the vendor details.
     */
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
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<VendorResponseDTO>>> getAllVendors() {
        log.info("Retrieving all vendors");
        return service.getAllVendors();
    }

    /**
     * Updates an existing vendor.
     *
     * @param id the ID of the vendor to update.
     * @param request the vendor request DTO containing the updated details of the vendor.
     * @return ResponseEntity with the updated vendor details.
     */
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
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteVendor(@PathVariable UUID id) {
        log.info("Deleting vendor with ID: {}", id);
        return service.deleteVendor(id);
    }
}
