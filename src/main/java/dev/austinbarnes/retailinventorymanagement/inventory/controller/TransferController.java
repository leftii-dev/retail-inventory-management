package dev.austinbarnes.retailinventorymanagement.inventory.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @PostMapping
    public ResponseEntity<ApiResponseDto<TransferResponseDTO>> createTransfer(TransferRequestDTO request) {
        log.info("Creating transfer with request: {}", request);
        return service.createTransfer(request);
    }

    /**
     * Retrieves a transfer by its ID.
     *
     * @param id the ID of the transfer to retrieve.
     * @return ResponseEntity with the transfer details.
     */
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
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<TransferResponseDTO>>> getAllTransfers() {
        log.info("Retrieving all transfers");
        return service.getAllTransfers();
    }

    /**
     * Updates an existing transfer by its ID.
     *
     * @param id the ID of the transfer to update.
     * @param request the transfer request DTO containing the updated details of the transfer.
     * @return ResponseEntity with the updated transfer details.
     */
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
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteTransfer(@PathVariable UUID id) {
        log.info("Deleting transfer with ID: {}", id);
        return service.deleteTransfer(id);
    }
}
