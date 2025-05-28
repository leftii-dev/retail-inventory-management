package dev.austinbarnes.retailinventorymanagement.product.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
@Slf4j
public class BrandController {
    private final BrandService service;

    /**
     * Creates a new brand.
     *
     * @param request the brand request DTO containing the details of the brand to create.
     * @return ResponseEntity with the created brand details.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<BrandResponseDTO>> createBrand(@RequestBody BrandRequestDTO request) {
        log.info("Creating brand: {}", request);
        return service.createBrand(request);
    }

    /**
     * Retrieves a brand by its ID.
     *
     * @param id the ID of the brand to retrieve.
     * @return ResponseEntity with the brand details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<BrandResponseDTO>> getBrandById(@PathVariable UUID id) {
        log.info("Retrieving brand with ID: {}", id);
        return service.getBrandById(id);
    }

    /**
     * Retrieves all brands.
     *
     * @return ResponseEntity with a list of all brands.
     */
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<BrandResponseDTO>>> getAllBrands() {
        log.info("Retrieving all brands");
        return service.getBrands();
    }

    /**
     * Updates an existing brand.
     *
     * @param id      the ID of the brand to update.
     * @param request the brand request DTO containing the updated details of the brand.
     * @return ResponseEntity with the updated brand details.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<BrandResponseDTO>> updateBrand(@PathVariable UUID id, @RequestBody BrandRequestDTO request) {
        log.info("Updating brand with ID: {}", id);
        return service.updateBrand(id, request);
    }

    /**
     * Deletes a brand by its ID.
     *
     * @param id the ID of the brand to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteBrand(@PathVariable UUID id) {
        log.info("Deleting brand with ID: {}", id);
        return service.deleteBrand(id);
    }
}
