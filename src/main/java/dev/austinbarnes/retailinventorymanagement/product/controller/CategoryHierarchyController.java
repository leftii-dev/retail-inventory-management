package dev.austinbarnes.retailinventorymanagement.product.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.*;
import dev.austinbarnes.retailinventorymanagement.product.service.CategoryHierarchyService;
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
 * CategoryHierarchyController handles HTTP requests for category hierarchy operations.
 * <p>
 * Provides endpoints to create, retrieve, update, and delete category hierarchies.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/category-hierarchy")
@Slf4j
public class    CategoryHierarchyController {
    private final CategoryHierarchyService service;

    /**
     * Creates a new category hierarchy.
     *
     * @param request The request DTO containing category hierarchy details.
     * @return ResponseEntity containing the created CategoryHierarchyResponseDTO.
     */
    @Operation(
            summary = "Create New Category Hierarchy",
            description = "Creates a new category hierarchy with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Category hierarchy created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {CategoryHierarchyResponseBasicDTO.class, CategoryHierarchyResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
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
    @Operation(
            summary = "Get Category Hierarchy by ID",
            description = "Retrieves a category hierarchy by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Category hierarchy retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {CategoryHierarchyResponseBasicDTO.class, CategoryHierarchyResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Category hierarchy not found")
    })
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
    @Operation(
            summary = "Get All Category Hierarchies",
            description = "Retrieves all category hierarchies."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Category hierarchies retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {CategoryHierarchyResponseBasicDTO.class, CategoryHierarchyResponseDetailDTO.class})
                    )
            )
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<CategoryHierarchyResponseDTO>>> getCategoryHierarchies(
            @ModelAttribute @Valid CategoryHierarchyFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        log.info("Received request to get category hierarchies");
        Sort sort = (sortBy != null && sortDirection != null) ?
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy) : Sort.unsorted();
        Pageable pageable = (page != null && size != null) ?
                PageRequest.of(page, size, sort) : Pageable.unpaged();
        return service.getCategoryHierarchies(filterDTO, pageable);
    }

    /**
     * Updates an existing CategoryHierarchy with the specified ID.
     *
     * @param id      The UUID of the CategoryHierarchy to update.
     * @param request The request DTO containing updated category hierarchy details.
     * @return ResponseEntity with the updated CategoryHierarchyResponseDTO.
     */
    @Operation(
            summary = "Update Category Hierarchy",
            description = "Updates an existing category hierarchy with the specified ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Category hierarchy updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {CategoryHierarchyResponseBasicDTO.class, CategoryHierarchyResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Category hierarchy not found"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
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
    @Operation(
            summary = "Delete Category Hierarchy",
            description = "Deletes a category hierarchy with the specified ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Category hierarchy deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = Void.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Category hierarchy not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteCategoryHierarchy(@PathVariable UUID id) {
        log.info("Received request to delete category hierarchy with id: {}", id);
        return service.deleteCategoryHierarchy(id);
    }

}
