package dev.austinbarnes.retailinventorymanagement.product.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.*;
import dev.austinbarnes.retailinventorymanagement.product.service.BrandService;
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
    @Operation(
            summary = "Create New Brand",
            description = "Creates a new brand with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Brand created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {BrandResponseBasicDTO.class, BrandResponseDetailDTO.class}
                            )
                    )
            )
    })
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
    @Operation(
            summary = "Get Brand by ID",
            description = "Retrieves a brand by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Brand retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {BrandResponseBasicDTO.class, BrandResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Brand not found")
    })
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
    @Operation(
            summary = "Get All Brands",
            description = "Retrieves a list of all brands with optional filtering and pagination."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Brands retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "array",
                                    oneOf = {BrandResponseBasicDTO.class, BrandResponseDetailDTO.class}
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<BrandResponseDTO>>> getAllBrands(
            @ModelAttribute @Valid BrandFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
            ) {
        log.info("Retrieving all brands");
        Sort sort = (sortBy != null && sortDirection != null) ?
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy) : Sort.unsorted();
        Pageable pageable = (page != null && size != null) ?
                PageRequest.of(page, size, sort) : Pageable.unpaged();
        return service.getBrands(filterDTO, pageable);
    }

    /**
     * Updates an existing brand.
     *
     * @param id      the ID of the brand to update.
     * @param request the brand request DTO containing the updated details of the brand.
     * @return ResponseEntity with the updated brand details.
     */
    @Operation(
            summary = "Update Brand",
            description = "Updates an existing brand by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Brand updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {BrandResponseBasicDTO.class, BrandResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Brand not found")
    })
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
    @Operation(
            summary = "Delete Brand",
            description = "Deletes a brand by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Brand deleted successfully"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteBrand(@PathVariable UUID id) {
        log.info("Deleting brand with ID: {}", id);
        return service.deleteBrand(id);
    }
}
