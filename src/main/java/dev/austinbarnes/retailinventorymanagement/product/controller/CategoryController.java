package dev.austinbarnes.retailinventorymanagement.product.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.*;
import dev.austinbarnes.retailinventorymanagement.product.service.CategoryService;
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
    @Operation(
            summary = "Create New Category",
            description = "Creates a new product category with the provided details."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Category created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {CategoryResponseBasicDTO.class, CategoryResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or category already exists")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<CategoryResponseDTO>> createCategory(
            @RequestBody @Valid CategoryRequestDTO request,
            @RequestParam(required = false) UUID parent
    ) {
        log.info("Creating category: {}", request);
        return service.createCategory(request, parent);
    }

    /**
     * Retrieves a product category by its ID.
     *
     * @param id the ID of the category to retrieve.
     * @return ResponseEntity with the category details.
     */
    @Operation(
            summary = "Get Category by ID",
            description = "Retrieves a product category by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {CategoryResponseBasicDTO.class, CategoryResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
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
    @Operation(
            summary = "Get All Categories",
            description = "Retrieves all product categories with optional filtering and pagination."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categories retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {CategoryResponseBasicDTO.class, CategoryResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid filter parameters")
    })
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
    @Operation(
            summary = "Update Category",
            description = "Updates an existing product category by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {CategoryResponseBasicDTO.class, CategoryResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
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
    @Operation(
            summary = "Delete Category",
            description = "Deletes a product category by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Category deleted successfully"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteCategory(@PathVariable UUID id) {
        log.info("Deleting category with ID: {}", id);
        return service.deleteCategory(id);
    }
}
