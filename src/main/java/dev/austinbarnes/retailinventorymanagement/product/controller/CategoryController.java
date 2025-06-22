package dev.austinbarnes.retailinventorymanagement.product.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryFilterDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.service.CategoryService;
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

/**
 * Controller for managing product categories.
 * Provides endpoints to create, retrieve, update, and delete categories.
 */
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService service;

    /**
     * Creates a new product category.
     *
     * @param request the category request DTO containing the details of the category to create.
     * @return ResponseEntity with the created category details.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<CategoryResponseDTO>> createCategory(@RequestBody @Valid CategoryRequestDTO request) {
        log.info("Creating category: {}", request);
        return service.createCategory(request);
    }

    /**
     * Retrieves a product category by its ID.
     *
     * @param id the ID of the category to retrieve.
     * @return ResponseEntity with the category details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<CategoryResponseDTO>> getCategoryById(@PathVariable UUID id) {
        log.info("Retrieving category with ID: {}", id);
        return service.getCategoryById(id);
    }

    /**
     * Retrieves all product categories.
     *
     * @return ResponseEntity with a list of all categories.
     */
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<CategoryResponseDTO>>> getAllCategories(
            @ModelAttribute @Valid CategoryFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        log.info("Retrieving all categories");
        Sort sort = (sortBy != null && sortDirection != null) ?
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy) : Sort.unsorted();
        Pageable pageable = (page != null && size != null) ?
                PageRequest.of(page, size, sort) : Pageable.unpaged();
        return service.getCategories(filterDTO, pageable);
    }
    /**
     * Updates an existing product category.
     *
     * @param id      the ID of the category to update.
     * @param request the category request DTO containing the updated details of the category.
     * @return ResponseEntity with the updated category details.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<CategoryResponseDTO>> updateCategory(@PathVariable UUID id, @RequestBody @Valid CategoryRequestDTO request) {
        log.info("Updating category with ID: {}", id);
        return service.updateCategory(id, request);
    }

    /**
     * Deletes a product category by its ID.
     *
     * @param id the ID of the category to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteCategory(@PathVariable UUID id) {
        log.info("Deleting category with ID: {}", id);
        return service.deleteCategory(id);
    }
}
