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
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ProductResponseDTO>>> getAllProducts() {
        log.info("Getting all products");
        return service.getAllProducts();
    }

    /**
     * Update Product
     *
     * @param id ID of product to update
     * @param request ProductRequestDTO with updated values
     * @return ProductResponseDTO representing updated Product
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ProductResponseDTO>> updateProduct(
            @PathVariable UUID id, @RequestBody @Valid ProductRequestDTO request) {
        log.info("Updating product {}", id);
        return service.updateProduct(id, request);
    }

    /**
     * Delete Product
     * @param id ID of Product to delete
     * @return API Response NO_CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteProduct(@PathVariable UUID id) {
        log.info("Deleting product {}", id);
        return service.deleteProduct(id);
    }
}
