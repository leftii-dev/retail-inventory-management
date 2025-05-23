package dev.austinbarnes.retailinventorymanagement.inventory.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryQtyChangeRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/inventory")
@AllArgsConstructor
@Slf4j
public class InventoryController {
    private final InventoryService service;

    /**
     * Creates a new inventory item.
     *
     * @param request The inventory request DTO containing inventory details.
     * @return ResponseEntity with the created inventory details.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<InventoryResponseDTO>> createInventory(@RequestBody @Valid InventoryRequestDTO request) {
        log.info("Create inventory request: {}", request);
        return service.createInventory(request);
    }

    /**
     * Updates (adds or subtracts) the quantity of an existing inventory item.
     *
     * @param request The inventory quantity change request DTO.
     * @return ResponseEntity with the updated inventory details.
     */
    @PutMapping
    public ResponseEntity<ApiResponseDto<InventoryResponseDTO>> updateInventory(@RequestBody @Valid InventoryQtyChangeRequestDTO request) {
        log.info("Update inventory request: {}", request);
        return service.updateInventory(request);
    }

    /**
     * Retrieves an inventory item by its ID.
     *
     * @param id The UUID of the inventory item.
     * @return ResponseEntity with the inventory details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<InventoryResponseDTO>> getInventory(@PathVariable UUID id) {
        log.info("Get inventory request: {}", id);
        return service.getInventoryById(id);
    }

    /**
     * Retrieves all inventory items, optionally filtered by location and/or product.
     *
     * @param locationId ID of the location to filter by (optional).
     * @param productId ID of the product to filter by (optional).
     * @return ResponseEntity with a list of inventory items.
     */
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<InventoryResponseDTO>>> getAllInventories(
            @RequestParam(required = false) UUID locationId,
            @RequestParam(required = false) UUID productId) {
        if(locationId != null && productId != null) {
            log.info("Get all inventories request with locationId: {} and productId: {}", locationId, productId);
            return service.getAllInventoriesByLocationAndProduct(locationId, productId);
        } else if (locationId != null) {
            log.info("Get all inventories request with locationId: {}", locationId);
            return service.getAllInventoriesByLocation(locationId);
        } else if (productId != null) {
            log.info("Get all inventories request with productId: {}", productId);
            return service.getAllInventoriesByProduct(productId);
        } else {
            log.info("Get all inventories request without filters");
            return service.getAllInventories();
        }
    }

    /**
     * Deletes an inventory item by its ID.
     *
     * @param id The UUID of the inventory item to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteInventory(@PathVariable UUID id) {
        log.info("Delete inventory request: {}", id);
        return service.deleteInventory(id);
    }
}
