package dev.austinbarnes.retailinventorymanagement.product.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductFilterDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import dev.austinbarnes.retailinventorymanagement.product.mapper.ProductMapper;
import dev.austinbarnes.retailinventorymanagement.product.repo.ProductRepository;
import dev.austinbarnes.retailinventorymanagement.product.specification.ProductSpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service class for managing products
 * Provides methods for create, get, getAll, update, and delete.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    /**
     * Creates new Product
     *
     * @param request DTO containing information for product creation.
     * @return ResponseEntity with ProductRequestDTO data.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_PRODUCT')")
    public ResponseEntity<ApiResponseDto<ProductResponseDTO>> createProduct(ProductRequestDTO request) {
        log.info("Creating product {}", request);
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(mapper.toEntity(request))) :
                mapper.toBasicDTO(repository.save(mapper.toEntity(request))));
    }

    /**
     * Retrieve a Product by ID
     * @param id ID of product to retrieve.
     * @return ResponseEntity with ProductResponseDTO as data.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<ProductResponseDTO>> getProductById(UUID id) {
        return ApiResponseDto.ok(repository.findById(id).map(product -> isManager() ?
                (ProductResponseDTO) mapper.toDetailDTO(product) :
                mapper.toBasicDTO(product))
                .orElseThrow(() -> new EntityNotFoundException("No Product found with ID: %s".formatted(id))));
    }

    /**
     * Retrieves a list of all Products
     *
     * @return List of ProductResponseDTO for all products
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<List<ProductResponseDTO>>> getAllProducts(
            ProductFilterDTO filterDTO, Pageable pageable
    ) {
        log.info("Getting all products");
        Specification<Product> spec = ProductSpecifications.applyFilters(filterDTO);
        return ApiResponseDto.ok(repository.findAll(spec, pageable).stream().map(
                product -> isManager() ?
                        (ProductResponseDTO) mapper.toDetailDTO(product) :
                        mapper.toBasicDTO(product)
            ).toList()
        );
    }

    /**
     * Updates a product
     *
     * @param id ID of the target product to update
     * @param request ProductRequestDTO providing the updated details.
     * @return ProductResponseDTO representing the updated product.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN', 'MANAGER') and hasAuthority('WRITE_PRODUCT')")
    public ResponseEntity<ApiResponseDto<ProductResponseDTO>> updateProduct(UUID id, ProductRequestDTO request) {
        log.info("Updating product {}", id);
        Product target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Product found with ID: %s".formatted(id)));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(target)) :
                mapper.toBasicDTO(repository.save(target)));
    }

    /**
     * Delete Product by ID
     *
     * @param id ID of the Product to delete
     * @return NoContent response
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_PRODUCT')")
    public ResponseEntity<ApiResponseDto<Void>> deleteProduct(UUID id) {
        log.info("Deleting product {}", id);
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
