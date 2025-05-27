package dev.austinbarnes.retailinventorymanagement.inventory.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.service.PurchaseOrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<PurchaseOrderItemResponseDTO>>> getAllPurchaseOrderItems() {
        log.info("Getting all purchase order items");
        return service.getAllPurchaseOrderItems();
    }

    /**
     * Updates an existing purchase order item.
     * This endpoint is accessible to users with roles MANAGER, ADMIN, or EMPLOYEE
     * and requires the WRITE_PO authority.
     *
     * @param id the UUID of the purchase order item to update
     * @param request the purchase order item request DTO containing updated item details
     * @return ResponseEntity with the updated purchase order item details
     */
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
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deletePurchaseOrderItem(@PathVariable UUID id) {
        log.info("Deleting purchase order item: {}", id);
        return service.deletePurchaseOrderItem(id);
    }
}
