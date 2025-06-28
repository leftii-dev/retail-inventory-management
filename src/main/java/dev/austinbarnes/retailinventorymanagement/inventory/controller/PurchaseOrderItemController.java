package dev.austinbarnes.retailinventorymanagement.inventory.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.*;
import dev.austinbarnes.retailinventorymanagement.inventory.service.PurchaseOrderItemService;
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

@RestController
@RequestMapping("/api/v1/purchase-order-items")
@RequiredArgsConstructor
@Slf4j
public class PurchaseOrderItemController {
    private final PurchaseOrderItemService service;

    /**
     * Creates a new purchase order item.
     * This endpoint is accessible to users with roles MANAGER, ADMIN, or EMPLOYEE
     * and requires the WRITE_PO authority.
     *
     * @param request the purchase order item request DTO containing item details
     * @return ResponseEntity with the created purchase order item details
     */
    @Operation(
            summary = "Create New Purchase Order Item",
            description = "Creates a new purchase order item with the provided details.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Purchase order item created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {PurchaseOrderItemResponseBasicDTO.class, PurchaseOrderItemResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or purchase order item already exists"),
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<PurchaseOrderItemResponseDTO>> createPurchaseOrderItem(
            @RequestBody @Valid PurchaseOrderItemRequestDTO request) {
        log.info("Creating purchase order item: {}", request);
        return service.createPurchaseOrderItem(request);
    }

    /**
     * Retrieves a purchase order item by its ID.
     * This endpoint is accessible to users with roles MANAGER, ADMIN, or EMPLOYEE
     * and requires the READ_PO authority.
     *
     * @param id the UUID of the purchase order item
     * @return ResponseEntity with the purchase order item details
     */
    @Operation(
            summary = "Get Purchase Order Item",
            description = "Retrieves a purchase order item by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Purchase order item retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {PurchaseOrderItemResponseBasicDTO.class, PurchaseOrderItemResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Purchase order item not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<PurchaseOrderItemResponseDTO>> getPurchaseOrderItem(@PathVariable UUID id) {
        log.info("Getting purchase order item: {}", id);
        return service.getPurchaseOrderItem(id);
    }

    /**
     * Retrieves all purchase order items.
     * This endpoint is accessible to users with roles MANAGER, ADMIN, or EMPLOYEE
     * and requires the READ_PO authority.
     *
     * @return ResponseEntity with a list of all purchase order items
     */
    @Operation(
            summary = "Get All Purchase Order Items",
            description = "Retrieves all purchase order items with optional filtering and pagination."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Purchase order items retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {PurchaseOrderItemResponseBasicDTO.class, PurchaseOrderItemResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<PurchaseOrderItemResponseDTO>>> getAllPurchaseOrderItems(
            @ModelAttribute @Valid PurchaseOrderItemFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        log.info("Getting all purchase order items");
        Sort sort = (sortBy != null && sortDirection != null)
                ? Sort.by(Sort.Direction.fromString(sortDirection), sortBy)
                : Sort.unsorted();

        Pageable pageable = (page != null && size != null)
                ? PageRequest.of(page, size, sort)
                : Pageable.unpaged();
        return service.getAllPurchaseOrderItems(filterDTO, pageable);
    }

    /**
     * Updates an existing purchase order item.
     * This endpoint is accessible to users with roles MANAGER, ADMIN, or EMPLOYEE
     * and requires the WRITE_PO authority.
     *
     * @param id      the UUID of the purchase order item to update
     * @param request the purchase order item request DTO containing updated item details
     * @return ResponseEntity with the updated purchase order item details
     */
    @Operation(
            summary = "Update Purchase Order Item",
            description = "Updates an existing purchase order item with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Purchase order item updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {PurchaseOrderItemResponseBasicDTO.class, PurchaseOrderItemResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
            @ApiResponse(responseCode = "404", description = "Purchase order item not found")
    })
            @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<PurchaseOrderItemResponseDTO>> updatePurchaseOrderItem(
            @PathVariable UUID id, @RequestBody PurchaseOrderItemRequestDTO request) {
        log.info("Updating purchase order item: {}", id);
        return service.updatePurchaseOrderItem(id, request);
    }

    /**
     * Deletes a purchase order item by its ID.
     * This endpoint is accessible to users with roles MANAGER, ADMIN, or EMPLOYEE
     * and requires the WRITE_PO authority.
     *
     * @param id the UUID of the purchase order item to delete
     * @return ResponseEntity indicating the result of the deletion operation
     */
    @Operation(
            summary = "Delete Purchase Order Item",
            description = "Deletes a purchase order item by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Purchase order item deleted successfully",
                    content = @Content(
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Purchase order item not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deletePurchaseOrderItem(@PathVariable UUID id) {
        log.info("Deleting purchase order item: {}", id);
        return service.deletePurchaseOrderItem(id);
    }
}
