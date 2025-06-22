package dev.austinbarnes.retailinventorymanagement.product.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller class for managing products.
 * Provides endpoints to create, retrieve, update, and delete products.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Slf4j
public class ProductController {
    private final ProductService service;

    /**
     * Creates a new product.
     *
     * @param request the product request DTO containing the details of the product to create.
     * @return ResponseEntity with the created product details.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<ProductResponseDTO>> createProduct(@RequestBody @Valid ProductRequestDTO request) {
        log.info("Creating product: {}", request);
        return service.createProduct(request);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve.
     * @return ResponseEntity with the product details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ProductResponseDTO>> getProductById(@PathVariable UUID id) {
        log.info("Retrieving product by ID: {}", id);
        return service.getProductById(id);
    }

    /**
     * Retrieves all products.
     *
     * @return ResponseEntity with a list of all products.
     */
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ProductResponseDTO>>> getAllProducts() {
        log.info("Retrieving all products");
        return service.getAllProducts();
    }

    /**
     * Updates an existing product.
     *
     * @param id      the ID of the product to update.
     * @param request the product request DTO containing the updated details of the product.
     * @return ResponseEntity with the updated product details.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ProductResponseDTO>> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductRequestDTO request) {
        log.info("Updating product with ID: {}", id);
        return service.updateProduct(id, request);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteProduct(@PathVariable UUID id) {
        log.info("Deleting product with ID: {}", id);
        return service.deleteProduct(id);
    }
}
