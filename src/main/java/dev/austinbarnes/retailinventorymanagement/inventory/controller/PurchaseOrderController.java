package dev.austinbarnes.retailinventorymanagement.inventory.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.*;
import dev.austinbarnes.retailinventorymanagement.inventory.service.PurchaseOrderService;
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
 * PurchaseOrderController handles requests related to purchase orders.
 * It provides endpoints for creating, retrieving, updating, and deleting purchase orders.
 */
@RestController
@RequestMapping("api/v1/purchase-orders")
@RequiredArgsConstructor
@Slf4j
public class    PurchaseOrderController {
    private final PurchaseOrderService service;

    /**
     * Creates a new purchase order.
     *
     * @param request the purchase order request containing details of the order
     * @return a response entity containing the created purchase order details
     */
    @Operation(
            summary = "Create New Purchase Order",
            description = "Creates a new purchase order with the provided details.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Purchase order created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {PurchaseOrderResponseBasicDTO.class, PurchaseOrderResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or purchase order already exists"),
    })
    @PostMapping
    ResponseEntity<ApiResponseDto<PurchaseOrderResponseDTO>> createPurchaseOrder(
            @Valid @RequestBody PurchaseOrderRequestDTO request) {
        log.info("Creating purchase order: {}", request);
        return service.createPurchaseOrder(request);
    }

    /**
     * Retrieves a purchase order by its ID.
     *
     * @param id the ID of the purchase order to retrieve
     * @return a response entity containing the purchase order details
     */
    @Operation(
            summary = "Get Purchase Order by ID",
            description = "Retrieves a purchase order by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Purchase order retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {PurchaseOrderResponseBasicDTO.class, PurchaseOrderResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Purchase order not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDto<PurchaseOrderResponseDTO>> getPurchaseOrderById(@PathVariable UUID id) {
        log.info("Retrieving purchase order with ID: {}", id);
        return service.getPurchaseOrderById(id);
    }

    /**
     * Retrieves all purchase orders.
     *
     * @return a response entity containing a list of all purchase orders
     */
    @Operation(
            summary = "Get All Purchase Orders",
            description = "Retrieves all purchase orders with optional filtering and pagination."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Purchase orders retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {PurchaseOrderResponseBasicDTO.class, PurchaseOrderResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
    @GetMapping
    ResponseEntity<ApiResponseDto<List<PurchaseOrderResponseDTO>>> getAllPurchaseOrders(
            @ModelAttribute @Valid PurchaseOrderFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        log.info("Retrieving all purchase orders");
        Sort sort = (sortBy != null && sortDirection != null)
                ? Sort.by(Sort.Direction.fromString(sortDirection), sortBy)
                : Sort.unsorted();

        Pageable pageable = (page != null && size != null)
                ? PageRequest.of(page, size, sort)
                : Pageable.unpaged();
        return service.getAllPurchaseOrders(filterDTO, pageable);
    }

    /**
     * Updates an existing purchase order.
     *
     * @param id      the ID of the purchase order to update
     * @param request the purchase order request containing updated details
     * @return a response entity containing the updated purchase order details
     */
    @Operation(
            summary = "Update Purchase Order",
            description = "Updates an existing purchase order with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Purchase order updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {PurchaseOrderResponseBasicDTO.class, PurchaseOrderResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Purchase order not found"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
    @PutMapping("/{id}")
    ResponseEntity<ApiResponseDto<PurchaseOrderResponseDTO>> updatePurchaseOrder(
            @Valid @RequestBody PurchaseOrderRequestDTO request, @PathVariable UUID id) {
        log.info("Updating purchase order: {}", request);
        return service.updatePurchaseOrder(id, request);
    }

    /**
     * Deletes a purchase order by its ID.
     *
     * @param id the ID of the purchase order to delete
     * @return a response entity indicating the result of the deletion
     */
    @Operation(
            summary = "Delete Purchase Order",
            description = "Deletes a purchase order by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Purchase order deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {Void.class})
                    )
            )
    })
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponseDto<Void>> deletePurchaseOrder(@PathVariable UUID id) {
        log.info("Deleting purchase order with ID: {}", id);
        return service.deletePurchaseOrder(id);
    }
}
