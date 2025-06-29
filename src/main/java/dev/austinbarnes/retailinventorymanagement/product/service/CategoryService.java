package dev.austinbarnes.retailinventorymanagement.product.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.entitycode.CodeGenerator;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryFilterDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Category;
import dev.austinbarnes.retailinventorymanagement.product.mapper.CategoryMapper;
import dev.austinbarnes.retailinventorymanagement.product.repo.CategoryRepository;
import dev.austinbarnes.retailinventorymanagement.product.specification.CategorySpecifications;
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
 * Service class for managing product categories.
 * Provides methods to create, retrieve, update, and delete categories.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final CodeGenerator codeGenerator;

    /**
     * Creates a new category.
     *
     * @param request the category request DTO containing the details of the category to create.
     * @return ResponseEntity with the created category details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'MANAGER') and hasAuthority('WRITE_PRODUCT')")
    public ResponseEntity<ApiResponseDto<CategoryResponseDTO>> createCategory(CategoryRequestDTO request) {
        log.info("Creating category: {}", request);
        Category newCategory = mapper.toEntity(request);
        newCategory.setCategoryCode(codeGenerator.generateCategoryCode());
        return ApiResponseDto.created(isManager()?
                mapper.toDetailDTO(repository.save(newCategory)) :
                mapper.toBasicDTO(repository.save(newCategory))
                );
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category to retrieve.
     * @return ResponseEntity with the category details.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<CategoryResponseDTO>> getCategoryById(UUID id) {
        log.info("Retrieving category with ID: {}", id);
        return repository.findById(id)
                .map(category -> ApiResponseDto.ok(isManager() ?
                        (CategoryResponseDTO) mapper.toDetailDTO(category) :
                        mapper.toBasicDTO(category)))
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + id));
    }

    /**
     * Retrieves all categories.
     *
     * @return ResponseEntity with a list of all categories.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<List<CategoryResponseDTO>>> getCategories(
            CategoryFilterDTO filterFTO, Pageable pageable
    ) {
        log.info("Retrieving all categories");
        Specification<Category> spec = CategorySpecifications.applyFilters(filterFTO);
        List<CategoryResponseDTO> categories = repository.findAll(spec, pageable).stream()
                .map(category -> isManager() ?
                        (CategoryResponseDTO) mapper.toDetailDTO(category) :
                        mapper.toBasicDTO(category))
                .toList();
        return ApiResponseDto.ok(categories);
    }

    /**
     * Updates an existing category.
     *
     * @param id      the ID of the category to update.
     * @param request the category request DTO containing the updated details.
     * @return ResponseEntity with the updated category details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'MANAGER') and hasAuthority('WRITE_PRODUCT')")
    public ResponseEntity<ApiResponseDto<CategoryResponseDTO>> updateCategory(UUID id, CategoryRequestDTO request) {
        log.info("Updating category with ID: {}", id);
        Category target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + id));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(target)) :
                mapper.toBasicDTO(repository.save(target)));
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id the ID of the category to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'MANAGER') and hasAuthority('WRITE_PRODUCT')")
    public ResponseEntity<ApiResponseDto<Void>> deleteCategory(UUID id) {
        log.info("Deleting category with ID: {}", id);
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
