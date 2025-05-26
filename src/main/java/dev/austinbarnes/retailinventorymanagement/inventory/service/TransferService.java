package dev.austinbarnes.retailinventorymanagement.inventory.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Transfer;
import dev.austinbarnes.retailinventorymanagement.inventory.mapper.TransferMapper;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.TransferRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferService {
    private final TransferRepository repository;
    private final TransferMapper mapper;

    /**
     * Creates a new transfer.
     *
     * @param request the transfer request DTO containing the details of the transfer to create.
     * @return ResponseEntity with the created transfer details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_TRANSFER')")
    public ResponseEntity<ApiResponseDto<TransferResponseDTO>> createTransfer(TransferRequestDTO request){
        log.info("Creating transfer with request: {}", request);
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(mapper.toEntity(request))) :
                mapper.toBasicDTO(repository.save(mapper.toEntity(request)))
        );
    }

    /**
     * Retrieves a transfer by its ID.
     *
     * @param id the ID of the transfer to retrieve.
     * @return ResponseEntity with the transfer details.
     */
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('READ_TRANSFER')")
    public ResponseEntity<ApiResponseDto<TransferResponseDTO>> getTransferById(UUID id) {
        log.info("Retrieving transfer with ID: {}", id);
        return repository.findById(id)
                .map(transfer -> ApiResponseDto.ok(isManager() ?
                        (TransferResponseDTO) mapper.toDetailDTO(transfer) :
                        mapper.toBasicDTO(transfer)))
                .orElseThrow(() -> new EntityNotFoundException("Transfer not found with ID: " + id));
    }

    /**
     * Retrieves all transfers.
     *
     * @return ResponseEntity with a list of all transfers.
     */
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('READ_TRANSFER')")
    public ResponseEntity<ApiResponseDto<List<TransferResponseDTO>>> getAllTransfers() {
        log.info("Retrieving all transfers");
        return ApiResponseDto.ok(repository.findAll().stream()
                .map(transfer -> isManager() ?
                        (TransferResponseDTO) mapper.toDetailDTO(transfer) :
                        mapper.toBasicDTO(transfer))
                .toList());
    }

    /**
     * Updates an existing transfer.
     *
     * @param id the ID of the transfer to update.
     * @param request the transfer request DTO containing the updated details.
     * @return ResponseEntity with the updated transfer details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_TRANSFER')")
    public ResponseEntity<ApiResponseDto<TransferResponseDTO>> updateTransfer(UUID id, TransferRequestDTO request) {
        log.info("Updating transfer with ID: {} and request: {}", id, request);
        Transfer target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transfer not found with ID: " + id));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(target)) :
                mapper.toBasicDTO(repository.save(target)));
    }

    /**
     * Deletes a transfer by its ID.
     *
     * @param id the ID of the transfer to delete.
     * @return ResponseEntity indicating the result of the deletion.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_TRANSFER')")
    public ResponseEntity<ApiResponseDto<Void>> deleteTransfer(UUID id) {
        log.info("Deleting transfer with ID: {}", id);
        repository.deleteById(id);
        return ApiResponseDto.noContent();
    }

    /**
     * Determines is the current user has manager or admin role.
     *
     * @return true if the user has manager or admin role, false otherwise.
     */
    private boolean isManager() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals("ROLE_MANAGER") || authority.getAuthority().equals("ROLE_ADMIN")
        );
    }
}
