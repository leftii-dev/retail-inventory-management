package dev.austinbarnes.retailinventorymanagement.product.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.mapper.ProductMapper;
import dev.austinbarnes.retailinventorymanagement.product.repo.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service class for managing products.
 * Provides methods to create, retrieve, update, and delete products.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    /**
     * Creates a new product.
     *
     * @param request the product request DTO containing the details of the product to create.
     * @return ResponseEntity with the created product details.
     */
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN') and hasPermission('PRODUCT_WRITE')")
    public ResponseEntity<ApiResponseDto<ProductResponseDTO>> createProduct(ProductRequestDTO request) {
        log.info("Creating product {}", request);
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(mapper.toEntity(request))) :
                mapper.toBasicDTO(repository.save(mapper.toEntity(request))));

    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve.
     * @return ResponseEntity with the product details.
     */
    public ResponseEntity<ApiResponseDto<ProductResponseDTO>> getProductById(UUID id) {
        log.info("Retrieving product by ID: {}", id);
        return repository.findById(id)
                .map(product -> ApiResponseDto.ok(isManager() ?
                        (ProductResponseDTO) mapper.toDetailDTO(product) :
                        mapper.toBasicDTO(product)))
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
    }

    /**
     * Retrieves all products.
     *
     * @return ResponseEntity with a list of all products.
     */
    public ResponseEntity<ApiResponseDto<List<ProductResponseDTO>>> getAllProducts() {
        log.info("Retrieving all products");
        List<ProductResponseDTO> products = repository.findAll().stream()
                .map(product -> isManager() ?
                        (ProductResponseDTO) mapper.toDetailDTO(product) :
                        mapper.toBasicDTO(product))
                .toList();
        return ApiResponseDto.ok(products);
    }

    /**
     * Updates an existing product.
     *
     * @param id      the ID of the product to update.
     * @param request the product request DTO containing the updated details of the product.
     * @return ResponseEntity with the updated product details.
     */
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN') and hasPermission('PRODUCT_WRITE')")
    public ResponseEntity<ApiResponseDto<ProductResponseDTO>> updateProduct(UUID id, ProductRequestDTO request) {
        log.info("Updating product with ID: {}", id);
        return repository.findById(id)
                .map(existingProduct -> {
                    mapper.updateEntityFromRequest(request, existingProduct);
                    return ApiResponseDto.ok(isManager() ?
                            (ProductResponseDTO) mapper.toDetailDTO(repository.save(existingProduct)) :
                            mapper.toBasicDTO(repository.save(existingProduct)));
                })
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete.
     * @return ResponseEntity with no content status.
     */
    public ResponseEntity<ApiResponseDto<Void>> deleteProduct(UUID id) {
        log.info("Deleting product with ID: {}", id);
        repository.deleteById(id);
        return ApiResponseDto.noContent();
    }

    /**
     * Determines is the current user has manager or admin role.
     *
     * @return true if the user has manager or admin role, false otherwise.
     */
    private boolean isManager() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals("ROLE_MANAGER") || authority.getAuthority().equals("ROLE_ADMIN")
        );
    }
}
