package dev.austinbarnes.retailinventorymanagement.inventory.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferFilterDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.service.TransferService;
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
