package dev.austinbarnes.retailinventorymanagement.inventory.controller;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherFilterDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.service.ReceivingVoucherService;
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
    @PostMapping
    public ResponseEntity<ApiResponseDto<ReceivingVoucherResponseDTO>> createReceivingVoucher(@Valid @RequestBody ReceivingVoucherRequestDTO request) {
        log.info("Creating Receiving Voucher: {}", request);
        return service.createReceivingVoucher(request);
    }

    /**
     * Retrieves a receiving voucher by its ID.
     *
     * @param id the ID of the receiving voucher to retrieve.
     * @return ResponseEntity with the receiving voucher details.
     */
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
     * @param id the ID of the receiving voucher to update.
     * @param request the receiving voucher request DTO containing the updated details.
     *
     * @return ResponseEntity with the updated receiving voucher details.
     */
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
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteReceivingVoucher(@PathVariable UUID id) {
        log.info("Deleting Receiving Voucher with ID: {}", id);
        return service.deleteReceivingVoucher(id);
    }
}
