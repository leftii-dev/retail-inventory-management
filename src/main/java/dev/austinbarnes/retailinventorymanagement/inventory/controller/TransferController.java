package dev.austinbarnes.retailinventorymanagement.inventory.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.*;
import dev.austinbarnes.retailinventorymanagement.inventory.service.TransferService;
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
@RequestMapping("/api/v1/transfers")
@Slf4j
public class TransferController {
    private final TransferService service;

    /**
     * Creates a new transfer.
     *
     * @param request the transfer request DTO containing the details of the transfer to create.
     * @return ResponseEntity with the created transfer details.
     */
    @Operation(
            summary = "Create New Transfer",
            description = "Creates a new transfer with the provided details.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Transfer created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {TransferResponseBasicDTO.class, TransferResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or receiving voucher already exists"),
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<TransferResponseDTO>> createTransfer(@RequestBody @Valid TransferRequestDTO request) {
        log.info("Creating transfer with request: {}", request);
        return service.createTransfer(request);
    }

    /**
     * Retrieves a transfer by its ID.
     *
     * @param id the ID of the transfer to retrieve.
     * @return ResponseEntity with the transfer details.
     */
    @Operation(
            summary = "Get Transfer by ID",
            description = "Retrieves a transfer by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transfer retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {TransferResponseBasicDTO.class, TransferResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Transfer not found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<TransferResponseDTO>> getTransferById(@PathVariable UUID id) {
        log.info("Retrieving transfer with ID: {}", id);
        return service.getTransferById(id);
    }

    /**
     * Retrieves all transfers.
     *
     * @return ResponseEntity with a list of all transfers.
     */
    @Operation(
            summary = "Get All Transfers",
            description = "Retrieves all transfers with optional filtering and pagination."
    )
@ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transfers retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {TransferResponseBasicDTO.class, TransferResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid filter parameters"),
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<TransferResponseDTO>>> getAllTransfers(
            @ModelAttribute @Valid TransferFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        log.info("Retrieving all transfers");
        Sort sort = (sortBy != null && sortDirection != null) ?
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy) :
                Sort.unsorted();
        Pageable pageable = (page != null && size != null) ?
                PageRequest.of(page, size, sort) :
                Pageable.unpaged();
        return service.getAllTransfers(filterDTO, pageable);
    }

    /**
     * Updates an existing transfer by its ID.
     *
     * @param id the ID of the transfer to update.
     * @param request the transfer request DTO containing the updated details of the transfer.
     * @return ResponseEntity with the updated transfer details.
     */
    @Operation(
            summary = "Update Transfer",
            description = "Updates an existing transfer by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transfer updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {TransferResponseBasicDTO.class, TransferResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Transfer not found"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<TransferResponseDTO>> updateTransfer(@PathVariable UUID id, @RequestBody TransferRequestDTO request) {
        log.info("Updating transfer with ID: {}", id);
        return service.updateTransfer(id, request);
    }

    /**
     * Deletes a transfer by its ID.
     *
     * @param id the ID of the transfer to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Operation(
            summary = "Delete Transfer",
            description = "Deletes a transfer by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Transfer deleted successfully"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteTransfer(@PathVariable UUID id) {
        log.info("Deleting transfer with ID: {}", id);
        return service.deleteTransfer(id);
    }

    /**
     * Finalizes a transfer, decrementing source inventory and incrementing destination inventory.
     *
     * @param id the ID of the transfer to finalize.
     * @return ResponseEntity with the finalized transfer details.
     */
    @Operation(
            summary = "Finalize Transfer",
            description = "Finalizes a transfer by updating inventory at source and destination locations."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transfer finalized successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {TransferResponseBasicDTO.class, TransferResponseDetailDTO.class})
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Transfer not found"),
            @ApiResponse(responseCode = "409", description = "Transfer already completed or cancelled"),
            @ApiResponse(responseCode = "400", description = "Insufficient stock at source location")
    })
    @PostMapping("/{id}/finalize")
    public ResponseEntity<ApiResponseDto<TransferResponseDTO>> finalizeTransfer(@PathVariable UUID id) {
        log.info("Finalizing transfer with ID: {}", id);
        return service.finalizeTransfer(id);
    }
}
