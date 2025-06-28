package dev.austinbarnes.retailinventorymanagement.inventory.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.*;
import dev.austinbarnes.retailinventorymanagement.inventory.service.TransferItemService;
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
@RequestMapping("/api/v1/transfer-items")
@Slf4j
public class TransferItemController {
    private final TransferItemService service;

    /**
     * Creates a new transfer item.
     *
     * @param request the transfer item request DTO containing the details of the transfer item to create.
     * @return ResponseEntity with the created transfer item details.
     */
    @Operation(
            summary = "Create New Transfer Item",
            description = "Creates a new transfer item with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Transfer Item created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {TransferItemResponseBasicDTO.class, TransferItemResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or transfer item already exists")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<TransferItemResponseDTO>> createTransferItem(@RequestBody @Valid TransferItemRequestDTO request) {
        log.info("Creating transfer item with request: {}", request);
        return service.createTransferItem(request);
    }

    /**
     * Retrieves a transfer item by its ID.
     *
     * @param id the ID of the transfer item to retrieve.
     * @return ResponseEntity with the transfer item details.
     */
    @Operation(
            summary = "Get Transfer Item by ID",
            description = "Retrieves a transfer item by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transfer Item retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {TransferItemResponseBasicDTO.class, TransferItemResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Transfer Item not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<TransferItemResponseDTO>> getTransferItemById(@PathVariable UUID id) {
        log.info("Retrieving transfer item with ID: {}", id);
        return service.getTransferItemById(id);
    }

    /**
     * Retrieves all transfer items.
     *
     * @return ResponseEntity with a list of all transfer items.
     */
    @Operation(
            summary = "Get All Transfer Items",
            description = "Retrieves all transfer items with optional filtering, pagination, and sorting."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transfer Items retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {TransferItemResponseBasicDTO.class, TransferItemResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<TransferItemResponseDTO>>> getAllTransferItems(
            @ModelAttribute @Valid TransferItemFilterDTO filterDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        log.info("Retrieving all transfer items");
        Sort sort = (sortBy != null && sortDirection != null)
                ? Sort.by(Sort.Direction.fromString(sortDirection), sortBy)
                : Sort.unsorted();
        Pageable pageable = (page != null && size != null)
                ? PageRequest.of(page, size, sort)
                : Pageable.unpaged();
        return service.getAllTransferItems(filterDTO, pageable);
    }

    /**
     * Updates an existing transfer item by its ID.
     *
     * @param id      the ID of the transfer item to update.
     * @param request the transfer item request DTO containing the updated details.
     * @return ResponseEntity with the updated transfer item details.
     */
    @Operation(
            summary = "Update Transfer Item",
            description = "Updates an existing transfer item by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transfer Item updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {TransferItemResponseBasicDTO.class, TransferItemResponseDetailDTO.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data or transfer item not found"),
            @ApiResponse(responseCode = "404", description = "Transfer Item not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<TransferItemResponseDTO>> updateTransferItem(
            @PathVariable UUID id, @RequestBody @Valid TransferItemRequestDTO request) {
        log.info("Updating transfer item with ID: {}", id);
        return service.updateTransferItem(id, request);
    }

    /**
     * Deletes a transfer item by its ID.
     *
     * @param id the ID of the transfer item to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Operation(
            summary = "Delete Transfer Item",
            description = "Deletes a transfer item by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Transfer Item deleted successfully",
                    content = @Content(
                            mediaType = "application/json")
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteTransferItem(@PathVariable UUID id) {
        log.info("Deleting transfer item with ID: {}", id);
        return service.deleteTransferItem(id);
    }
}
