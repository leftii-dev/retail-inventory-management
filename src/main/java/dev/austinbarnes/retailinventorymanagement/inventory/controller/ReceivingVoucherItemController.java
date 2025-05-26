package dev.austinbarnes.retailinventorymanagement.inventory.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.service.ReceivingVoucherItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/receiving-voucher-items")
@Slf4j
public class ReceivingVoucherItemController {
    private final ReceivingVoucherItemService service;

    /**
     * Creates a new receiving voucher item.
     *
     * @param request the receiving voucher item request DTO containing the details of the item to create.
     * @return ResponseEntity with the created receiving voucher item details.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<ReceivingVoucherItemResponseDTO>> createReceivingVoucherItem(
            @Valid @RequestBody ReceivingVoucherItemRequestDTO request) {
        log.info("Creating receiving voucher item: {}", request);
        return service.createReceivingVoucherItem(request);
    }

    /**
     * Retrieves a receiving voucher item by its ID.
     *
     * @param id the ID of the receiving voucher item to retrieve.
     * @return ResponseEntity with the receiving voucher item details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ReceivingVoucherItemResponseDTO>> getReceivingVoucherItemById(
            @PathVariable UUID id) {
        log.info("Retrieving receiving voucher item with ID: {}", id);
        return service.getReceivingVoucherItemById(id);
    }

    /**
     * Retrieves all receiving voucher items.
     *
     * @return ResponseEntity with a list of all receiving voucher items.
     */
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ReceivingVoucherItemResponseDTO>>> getAllReceivingVoucherItems() {
        log.info("Retrieving all receiving voucher items");
        return service.getAllReceivingVoucherItems();
    }

    /**
     * Updates an existing receiving voucher item.
     *
     * @param id      the ID of the receiving voucher item to update.
     * @param request the receiving voucher item request DTO containing the updated details.
     * @return ResponseEntity with the updated receiving voucher item details.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ReceivingVoucherItemResponseDTO>> updateReceivingVoucherItem(
            @PathVariable UUID id,
            @Valid @RequestBody ReceivingVoucherItemRequestDTO request) {
        log.info("Updating receiving voucher item with ID: {}", id);
        return service.updateReceivingVoucherItem(id, request);
    }

    /**
     * Deletes a receiving voucher item.
     *
     * @param id the ID of the receiving voucher item to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteReceivingVoucherItem(@PathVariable UUID id) {
        log.info("Deleting receiving voucher item with ID: {}", id);
        return service.deleteReceivingVoucherItem(id);
    }
}
