package dev.austinbarnes.retailinventorymanagement.product.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.service.CategoryHierarchyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * CategoryHierarchyController handles HTTP requests for category hierarchy operations.
 * <p>
 * Provides endpoints to create, retrieve, update, and delete category hierarchies.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/category-hierarchy")
@Slf4j
public class CategoryHierarchyController {
    private final CategoryHierarchyService service;

    /**
     * Creates a new category hierarchy.
     *
     * @param request The request DTO containing category hierarchy details.
     * @return ResponseEntity containing the created CategoryHierarchyResponseDTO.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<CategoryHierarchyResponseDTO>> createCategoryHierarchy(
            @RequestBody @Valid CategoryHierarchyRequestDTO request) {
        log.info("Received request to create category hierarchy: {}", request);
        return service.createCategoryHierarchy(request);
    }

    /**
     * Retrieves a category hierarchy by its unique identifier.
     *
     * @param id The UUID of the category hierarchy to retrieve.
     * @return ResponseEntity containing the requested CategoryHierarchyResponseDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<CategoryHierarchyResponseDTO>> getCategoryHierarchy(@PathVariable UUID id) {
        log.info("Received request to get category hierarchy with id: {}", id);
        return service.getCategoryHierarchy(id);
    }

    /**
     * Retrieves all category hierarchies.
     *
     * @return ResponseEntity with a list of all CategoryHierarchyResponseDTOs.
     */
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<CategoryHierarchyResponseDTO>>> getCategoryHierarchies() {
        log.info("Received request to get category hierarchies");
        return service.getCategoryHierarchies();
    }
    /**
     * Updates an existing CategoryHierarchy with the specified ID.
     *
     * @param id The UUID of the CategoryHierarchy to update.
     * @param request The request DTO containing updated category hierarchy details.
     * @return ResponseEntity with the updated CategoryHierarchyResponseDTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<CategoryHierarchyResponseDTO>> updateCategoryHierarchy(@PathVariable UUID id, @RequestBody @Valid CategoryHierarchyRequestDTO request) {
        log.info("Received request to update category hierarchy with id: {}", id);
        return service.updateCategoryHierarchy(id, request);
    }

    /**
     * Delete CategoryHierarchy with specified id
     *
     * @param id identifier of CategoryHierarchy to delete.
     * @return Void ApiResponse (no content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteCategoryHierarchy(@PathVariable UUID id) {
        log.info("Received request to delete category hierarchy with id: {}", id);
        return service.deleteCategoryHierarchy(id);
    }

}
