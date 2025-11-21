package dev.austinbarnes.retailinventorymanagement.product.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.entitycode.CodeGenerator;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.*;
import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import dev.austinbarnes.retailinventorymanagement.product.entity.ProductImage;
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

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
    private final CodeGenerator codeGenerator;

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
        Product newProduct = mapper.toEntity(request);
        newProduct.setProductCode(codeGenerator.generateProductCode());
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(newProduct)) :
                mapper.toBasicDTO(repository.save(newProduct)));
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
        Product target = findProductOrThrow(id);
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
     * Creates a new Product Image
     *
     * @param productID ID of the product to add the image to
     * @param request the ProductImageRequestDTO
     * @return Created Response with Image DTO
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_PRODUCT')")
    public ResponseEntity<ApiResponseDto<ProductImageResponseDTO>> addImage(UUID productID, ProductImageRequestDTO request) {
        log.info("Adding image to product {}", productID);
        Product product = findProductOrThrow(productID);

        ProductImage image = mapper.toImageEntity(request);
        product.addImage(image);


        if(request.isDefault()){
            product.setDefaultImage(image);
        }

        repository.save(product);

        return ApiResponseDto.created(isManager() ?
                mapper.toImageDetailDTO(image) :
                mapper.toImageBasicDTO(image));
    }

    /**
     * Updates a ProductImage on a specified Product
     * @param productId ID of the product the image belongs to
     * @param imageId ID of the image to update
     * @param request ProductImageRequestDTO with new values
     * @return OK response with Image DTO
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_PRODUCT')")
    public ResponseEntity<ApiResponseDto<ProductImageResponseDTO>> updateImage(
            UUID productId, UUID imageId, ProductImageRequestDTO request
    ) {
        log.info("Updating image {} for product {}", imageId, productId);
        Product product = findProductOrThrow(productId);

        ProductImage image = findImageOrThrow(product, imageId);

        mapper.updateImageFromRequest(request, image);
        repository.save(product);

        return ApiResponseDto.ok(isManager() ?
                mapper.toImageDetailDTO(image) :
                mapper.toImageBasicDTO(image));
    }

    /**
     * Sets the dafault image of a product
     * @param productId ID of the product
     * @param imageId ID of the image
     * @return NoContent response
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_PRODUCT')")
    public ResponseEntity<ApiResponseDto<Void>> setDefaultImage(UUID productId, UUID imageId) {
        log.info("Setting image {} as default for product {}", imageId, productId);
        Product product = findProductOrThrow(productId);

        product.setDefaultImage(findImageOrThrow(product, imageId));

        repository.save(product);
        return ApiResponseDto.noContent();
    }

    /**
     * Removes an image from a specified Product
     * @param productId ID of the product
     * @param imageId ID of the image to remove
     * @return NoContent response
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_PRODUCT')")
    public ResponseEntity<ApiResponseDto<Void>> deleteImage(UUID productId, UUID imageId){
        log.info("Deleting image {} from product {}", imageId, productId);
        Product product = findProductOrThrow(productId);

        ProductImage image = findImageOrThrow(product, imageId);
        product.removeImage(image);
        repository.save(product);

        return ApiResponseDto.noContent();
    }

    /**
     * Retrieves a specified products list of images
     * @param productId ID of the product
     * @return Ok response with list of ProductImageResponseDTOs
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<List<ProductImageResponseDTO>>> getProductImages(UUID productId) {
        log.info("Getting image from product {}", productId);
        Product product = findProductOrThrow(productId);

        return ApiResponseDto.ok(isManager() ?
                mapper.toImageDetailDTOList(product.getImages()).stream()
                        .map(dto -> (ProductImageResponseDTO) dto).toList() :
                mapper.toImageBasicDTOList(product.getImages()).stream()
                        .map(dto -> (ProductImageResponseDTO) dto).toList());
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_PRODUCT')")
    public ResponseEntity<ApiResponseDto<Void>> reorderImages(UUID productId, List<UUID> imageIds) {
        log.info("Reordering images for product {}", productId);
        Product product = findProductOrThrow(productId);

        List<UUID> productImageIds = product.getImages().stream()
                .map(ProductImage::getId)
                .toList();

        if(!new HashSet<>(productImageIds).containsAll(imageIds) || productImageIds.size() != imageIds.size()) {
            throw new IllegalArgumentException("Invalid image IDs provided for reordering");
        }

        for(int i = 0; i < imageIds.size(); i++) {
            UUID imageId = imageIds.get(i);
            ProductImage image = findImageOrThrow(product, imageId);
            image.setDisplayOrder(i);
        }

        repository.save(product);
        return ApiResponseDto.noContent();
    }

    /**
     * Finds a product by ID or throws EntityNotFoundException
     *
     * @param id Product ID
     * @return Product entity
     */
    private Product findProductOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Product found with ID: %s".formatted(id)));
    }

    /**
     * Finds an image within a product or throws EntityNotFoundException
     *
     * @param product Product entity
     * @param imageId Image ID
     * @return ProductImage entity
     */
    private ProductImage findImageOrThrow(Product product, UUID imageId) {
        return product.getImages().stream()
                .filter(img -> Objects.equals(img.getId(), imageId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "No Image found with ID: %s for Product: %s".formatted(imageId, product.getId())));
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
