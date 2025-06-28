package dev.austinbarnes.retailinventorymanagement.inventory.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.*;
import dev.austinbarnes.retailinventorymanagement.inventory.service.ReceivingVoucherItemService;
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
    @Operation(
            summary = "Create New Receiving Voucher Item",
            description = "Creates a new receiving voucher item with the provided details.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Receiving Voucher Item created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {ReceivingVoucherItemResponseBasicDTO.class, ReceivingVoucherItemResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or receiving voucher item already exists"),
    })
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
    @Operation(
            summary = "Get Receiving Voucher Item by ID",
            description = "Retrieves a receiving voucher item by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Receiving Voucher Item retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {ReceivingVoucherItemResponseBasicDTO.class, ReceivingVoucherItemResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Receiving Voucher Item not found")
    })
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
    @Operation(
            summary = "Get All Receiving Voucher Items",
            description = "Retrieves all receiving voucher items with optional filtering and pagination."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Receiving Voucher Items retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {ReceivingVoucherItemResponseBasicDTO.class, ReceivingVoucherItemResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ReceivingVoucherItemResponseDTO>>> getAllReceivingVoucherItems(
            @ModelAttribute @Valid ReceivingVoucherItemFIlterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        log.info("Retrieving all receiving voucher items");
        Sort sort = (sortBy != null && sortDirection != null)
                ? Sort.by(Sort.Direction.fromString(sortDirection), sortBy)
                : Sort.unsorted();
        Pageable pageable = (page != null && size != null)
                ? PageRequest.of(page, size, sort)
                : Pageable.unpaged();
        return service.getAllReceivingVoucherItems(filterDTO, pageable);
    }

    /**
     * Updates an existing receiving voucher item.
     *
     * @param id      the ID of the receiving voucher item to update.
     * @param request the receiving voucher item request DTO containing the updated details.
     * @return ResponseEntity with the updated receiving voucher item details.
     */
    @Operation(
            summary = "Update Receiving Voucher Item",
            description = "Updates an existing receiving voucher item with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Receiving Voucher Item updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {ReceivingVoucherItemResponseBasicDTO.class, ReceivingVoucherItemResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
            @ApiResponse(responseCode = "404", description = "Receiving Voucher Item not found")
    })
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
    @Operation(
            summary = "Delete Receiving Voucher Item",
            description = "Deletes a receiving voucher item by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Receiving Voucher Item deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "void")
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteReceivingVoucherItem(@PathVariable UUID id) {
        log.info("Deleting receiving voucher item with ID: {}", id);
        return service.deleteReceivingVoucherItem(id);
    }
}
