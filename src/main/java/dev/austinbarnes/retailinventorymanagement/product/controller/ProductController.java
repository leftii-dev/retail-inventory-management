package dev.austinbarnes.retailinventorymanagement.product.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.*;
import dev.austinbarnes.retailinventorymanagement.product.service.ProductService;
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
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService service;

    /**
     * Request to create new product
     *
     * @param request ProductRequestDTO containing product information
     * @return ProductResponseDTO for new product.
     */
    @Operation(
            summary = "Create New Product",
            description = "Creates a new product with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Product created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {ProductResponseBasicDTO.class, ProductResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or product already exists")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<ProductResponseDTO>> createProduct(@RequestBody @Valid ProductRequestDTO request) {
        log.info("Creating product {}", request);
        return service.createProduct(request);
    }

    /**
     * Retrieve a product by its ID
     *
     * @param id ID of the product requested
     * @return ProductResponseDTO representing the product retrieved.
     */
    @Operation(
            summary = "Get Product by ID",
            description = "Retrieves a product by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {ProductResponseBasicDTO.class, ProductResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ProductResponseDTO>> getProduct(@PathVariable UUID id) {
        log.info("Getting product {}", id);
        return service.getProductById(id);
    }

    /**
     * Retrieve all products
     *
     * @return List of ProductResponseDTO
     */
    @Operation(
            summary = "Get All Products",
            description = "Retrieves all products with optional filtering and pagination."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Products retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {ProductResponseBasicDTO.class, ProductResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid filter parameters")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ProductResponseDTO>>> getAllProducts(
            @ModelAttribute @Valid ProductFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        log.info("Getting all products");
        Sort sort = (sortBy != null && sortDirection != null) ?
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy) : Sort.unsorted();
        Pageable pageable = (page != null && size != null) ?
                PageRequest.of(page, size, sort) : Pageable.unpaged();
        return service.getAllProducts(filterDTO, pageable);
    }

    /**
     * Update Product
     *
     * @param id      ID of product to update
     * @param request ProductRequestDTO with updated values
     * @return ProductResponseDTO representing updated Product
     */
    @Operation(
            summary = "Update Product",
            description = "Updates an existing product with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {ProductResponseBasicDTO.class, ProductResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ProductResponseDTO>> updateProduct(
            @PathVariable UUID id, @RequestBody @Valid ProductRequestDTO request) {
        log.info("Updating product {}", id);
        return service.updateProduct(id, request);
    }

    /**
     * Delete Product
     *
     * @param id ID of Product to delete
     * @return API Response NO_CONTENT
     */
    @Operation(
            summary = "Delete Product",
            description = "Deletes a product by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Product deleted successfully"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteProduct(@PathVariable UUID id) {
        log.info("Deleting product {}", id);
        return service.deleteProduct(id);
    }

    @GetMapping("/{productId}/images")
    public ResponseEntity<ApiResponseDto<List<ProductImageResponseDTO>>> getProductImages(@PathVariable UUID productId) {
        return service.getProductImages(productId);
    }

    @PostMapping("/{productId}/images")
    public ResponseEntity<ApiResponseDto<ProductImageResponseDTO>> addImage(
            @PathVariable UUID productId,
            @Valid @RequestBody ProductImageRequestDTO request
    ) {
        return service.addImage(productId, request);
    }

    @PutMapping("{productId}/images/{imageId}")
    public ResponseEntity<ApiResponseDto<ProductImageResponseDTO>> updateImage(
            @PathVariable UUID productId,
            @PathVariable UUID imageId,
            @Valid @RequestBody ProductImageRequestDTO request
    ) {
        return service.updateImage(productId, imageId, request);
    }

    @PatchMapping("/{productId}/images/{imageId}/default")
    public ResponseEntity<ApiResponseDto<Void>> setDefaultImage(
            @PathVariable UUID productId,
            @PathVariable UUID imageId
    ){
        return service.setDefaultImage(productId, imageId);
    }

    @DeleteMapping("/{productId}/images/{imageId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteImage(
            @PathVariable UUID productId,
            @PathVariable UUID imageId
    ) {
        return service.deleteImage(productId, imageId);
    }

    @PutMapping("/{productId}/images/reorder")
    public ResponseEntity<ApiResponseDto<Void>> reorderImages(
            @PathVariable UUID productId,
            @RequestBody List<UUID> imageIds
    ) {
        return service.reorderImages(productId, imageIds);
    }

}
