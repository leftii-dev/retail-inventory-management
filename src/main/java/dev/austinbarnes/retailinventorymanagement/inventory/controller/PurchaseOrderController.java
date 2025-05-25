package dev.austinbarnes.retailinventorymanagement.inventory.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.service.PurchaseOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class PurchaseOrderController {
    private final PurchaseOrderService service;

    /**
     * Creates a new purchase order.
     *
     * @param request the purchase order request containing details of the order
     * @return a response entity containing the created purchase order details
     */
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
    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDto<PurchaseOrderResponseDTO>> getPurchaseOrderById(UUID id) {
        log.info("Retrieving purchase order with ID: {}", id);
        return service.getPurchaseOrderById(id);
    }

    /**
     * Retrieves all purchase orders.
     *
     * @return a response entity containing a list of all purchase orders
     */
    @GetMapping
    ResponseEntity<ApiResponseDto<List<PurchaseOrderResponseDTO>>> getAllPurchaseOrders() {
        log.info("Retrieving all purchase orders");
        return service.getAllPurchaseOrders();
    }

    /**
     * Updates an existing purchase order.
     *
     * @param id the ID of the purchase order to update
     * @param request the purchase order request containing updated details
     * @return a response entity containing the updated purchase order details
     */
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
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponseDto<Void>> deletePurchaseOrder(@PathVariable UUID id) {
        log.info("Deleting purchase order with ID: {}", id);
        return service.deletePurchaseOrder(id);
    }
}
