package dev.austinbarnes.retailinventorymanagement.product.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.product.dto.discount.*;
import dev.austinbarnes.retailinventorymanagement.product.service.DiscountService;
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
 * Controller class for managing discounts.
 * Provides endpoints to create, retrieve, update, and delete discounts.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discounts")
@Slf4j
public class DiscountController {
    private final DiscountService service;

    /**
     * Creates a new discount.
     *
     * @param request the discount request DTO containing the details of the discount to create.
     * @return ResponseEntity with the created discount details.
     */
    @Operation(
            summary = "Create New Discount",
            description = "Creates a new discount with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Discount created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {DiscountResponseBasicDTO.class, DiscountResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or discount already exists")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<DiscountResponseDTO>> createDiscount(@RequestBody @Valid DiscountRequestDTO request) {
        log.info("Create discount request: {}", request);
        return service.createDiscount(request);
    }

    /**
     * Retrieves a discount by its ID.
     *
     * @param id the ID of the discount to retrieve.
     * @return ResponseEntity with the discount details.
     */
    @Operation(
            summary = "Get Discount by ID",
            description = "Retrieves a discount by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Discount retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {DiscountResponseBasicDTO.class, DiscountResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Discount not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<DiscountResponseDTO>> getDiscountById(@PathVariable UUID id) {
        log.info("Get discount by ID: {}", id);
        return service.getDiscountById(id);
    }

    /**
     * Retrieves all discounts.
     *
     * @return ResponseEntity with a list of all discounts.
     */
    @Operation(
            summary = "Get All Discounts",
            description = "Retrieves all discounts with optional filtering and pagination."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Discounts retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {DiscountResponseBasicDTO.class, DiscountResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid filter parameters")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<DiscountResponseDTO>>> getAllDiscounts(
            @ModelAttribute @Valid DiscountFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        log.info("Get all discounts");
        Sort sort = (sortBy != null && sortDirection != null) ?
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy) : Sort.unsorted();
        Pageable pageable = (page != null && size != null) ?
                PageRequest.of(page, size, sort) : Pageable.unpaged();
        return service.getAllDiscounts(filterDTO, pageable);
    }

    /**
     * Updates an existing discount.
     *
     * @param id      the ID of the discount to update.
     * @param request the discount request DTO containing the updated details of the discount.
     * @return ResponseEntity with the updated discount details.
     */
    @Operation(
            summary = "Update Discount",
            description = "Updates an existing discount with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Discount updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {DiscountResponseBasicDTO.class, DiscountResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Discount not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<DiscountResponseDTO>> updateDiscount(
            @PathVariable UUID id, @RequestBody DiscountRequestDTO request
    ) {
        log.info("Update discount with ID: {}, request: {}", id, request);
        return service.updateDiscount(id, request);
    }

    /**
     * Deletes a discount by its ID.
     *
     * @param id the ID of the discount to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Operation(
            summary = "Delete Discount",
            description = "Deletes a discount by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Discount deleted successfully"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteDiscount(@PathVariable UUID id) {
        log.info("Delete discount with ID: {}", id);
        return service.deleteDiscount(id);
    }
}
