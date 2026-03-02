package dev.austinbarnes.retailinventorymanagement.inventory.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.*;
import dev.austinbarnes.retailinventorymanagement.inventory.service.ReceivingVoucherService;
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
@RequestMapping("/api/v1/receiving-vouchers")
@RequiredArgsConstructor
@Slf4j
public class ReceivingVoucherController {
    private final ReceivingVoucherService service;

    /**
     * Creates a new receiving voucher.
     *
     * @param request the receiving voucher request DTO containing the details of the receiving voucher to create.
     * @return ResponseEntity with the created receiving voucher details.
     */
    @Operation(
            summary = "Create New Receiving Voucher",
            description = "Creates a new receiving voucher with the provided details.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Receiving Voucher created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {ReceivingVoucherResponseBasicDTO.class, ReceivingVoucherResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or receiving voucher already exists"),
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<ReceivingVoucherResponseDTO>> createReceivingVoucher(@Valid @RequestBody ReceivingVoucherRequestDTO request) {
        log.info("Creating Receiving Voucher: {}", request);
        return service.createReceivingVoucher(request);
    }

    /**
     * Creates a new receiving voucher pre-populated from a purchase order's line items.
     *
     * @param purchaseOrderId the ID of the purchase order to build from.
     * @return ResponseEntity with the created receiving voucher details.
     */
    @Operation(
            summary = "Create Receiving Voucher from Purchase Order",
            description = "Creates a receiving voucher pre-populated with items and fields from the given purchase order."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Receiving Voucher created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {ReceivingVoucherResponseBasicDTO.class, ReceivingVoucherResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Purchase Order or Location not found")
    })
    @PostMapping("/from-purchase-order/{purchaseOrderId}")
    public ResponseEntity<ApiResponseDto<ReceivingVoucherResponseDTO>> createReceivingVoucherFromPurchaseOrder(
            @PathVariable UUID purchaseOrderId) {
        log.info("Creating Receiving Voucher from Purchase Order ID: {}", purchaseOrderId);
        return service.createReceivingVoucherFromPurchaseOrder(purchaseOrderId);
    }

    /**
     * Retrieves a receiving voucher by its ID.
     *
     * @param id the ID of the receiving voucher to retrieve.
     * @return ResponseEntity with the receiving voucher details.
     */
    @Operation(
            summary = "Get Receiving Voucher by ID",
            description = "Retrieves a receiving voucher by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Receiving Voucher retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {ReceivingVoucherResponseBasicDTO.class, ReceivingVoucherResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Receiving Voucher not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ReceivingVoucherResponseDTO>> getReceivingVoucherById(@PathVariable UUID id) {
        log.info("Retrieving Receiving Voucher with ID: {}", id);
        return service.getReceivingVoucherById(id);
    }

    /**
     * Retrieves all receiving vouchers.
     *
     * @return ResponseEntity with a list of all receiving vouchers.
     */
    @Operation(
            summary = "Get All Receiving Vouchers",
            description = "Retrieves all receiving vouchers with optional filtering and pagination."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Receiving Vouchers retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {ReceivingVoucherResponseBasicDTO.class, ReceivingVoucherResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ReceivingVoucherResponseDTO>>> getAllReceivingVouchers(
            @ModelAttribute @Valid ReceivingVoucherFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        log.info("Retrieving all Receiving Vouchers");
        Sort sort = (sortBy != null && sortDirection != null) ?
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy) :
                Sort.unsorted();
        Pageable pageable = (page != null && size != null) ?
                PageRequest.of(page, size, sort) :
                Pageable.unpaged();
        return service.getAllReceivingVouchers(filterDTO, pageable);
    }

    /**
     * Updates an existing receiving voucher.
     *
     * @param id      the ID of the receiving voucher to update.
     * @param request the receiving voucher request DTO containing the updated details.
     * @return ResponseEntity with the updated receiving voucher details.
     */
    @Operation(
            summary = "Update Receiving Voucher",
            description = "Updates an existing receiving voucher with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Receiving Voucher updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {ReceivingVoucherResponseBasicDTO.class, ReceivingVoucherResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or receiving voucher not found"),
            @ApiResponse(responseCode = "404", description = "Receiving Voucher not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ReceivingVoucherResponseDTO>> updateReceivingVoucher(
            @PathVariable UUID id, @Valid @RequestBody ReceivingVoucherRequestDTO request) {
        log.info("Updating Receiving Voucher with ID: {}", id);
        return service.updateReceivingVoucher(id, request);
    }

    /**
     * Deletes a receiving voucher by its ID.
     *
     * @param id the ID of the receiving voucher to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Operation(
            summary = "Delete Receiving Voucher",
            description = "Deletes a receiving voucher by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Receiving Voucher deleted successfully"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteReceivingVoucher(@PathVariable UUID id) {
        log.info("Deleting Receiving Voucher with ID: {}", id);
        return service.deleteReceivingVoucher(id);
    }

    /**
     * Finalizes a receiving voucher, creating or incrementing inventory records for each item.
     *
     * @param id the ID of the receiving voucher to finalize.
     * @return ResponseEntity with the finalized receiving voucher details.
     */
    @Operation(
            summary = "Finalize Receiving Voucher",
            description = "Finalizes a receiving voucher by updating inventory at the receiving location."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Receiving Voucher finalized successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {ReceivingVoucherResponseBasicDTO.class, ReceivingVoucherResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Receiving Voucher not found"),
            @ApiResponse(responseCode = "409", description = "Receiving Voucher already completed or cancelled")
    })
    @PostMapping("/{id}/finalize")
    public ResponseEntity<ApiResponseDto<ReceivingVoucherResponseDTO>> finalizeReceivingVoucher(@PathVariable UUID id) {
        log.info("Finalizing Receiving Voucher with ID: {}", id);
        return service.finalizeReceivingVoucher(id);
    }
}
