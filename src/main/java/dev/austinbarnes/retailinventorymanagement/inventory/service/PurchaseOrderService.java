package dev.austinbarnes.retailinventorymanagement.inventory.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrder;
import dev.austinbarnes.retailinventorymanagement.inventory.mapper.PurchaseOrderMapper;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.PurchaseOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * PurchaseOrderService handles all operations related to purchase orders.
 * <p>
 * It provides methods to create, update, retrieve, and delete purchase orders.
 */
@Service
@AllArgsConstructor
@Slf4j
public class PurchaseOrderService {
    private final PurchaseOrderRepository repository;
    private final PurchaseOrderMapper mapper;

    /**
     * Creates a new purchase order.
     *
     * @param request the purchase order request DTO containing the details of the purchase order to create.
     * @return ResponseEntity with the created purchase order details.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('WRITE_PO')")
    public ResponseEntity<ApiResponseDto<PurchaseOrderResponseDTO>> createPurchaseOrder(PurchaseOrderRequestDTO request) {
        log.info("Creating purchase order: {}", request);
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(mapper.toEntity(request))) :
                mapper.toBasicDTO(repository.save(mapper.toEntity(request)))
        );
    }

    /**
     * Retrieves a purchase order by its ID.
     *
     * @param id the ID of the purchase order to retrieve.
     * @return ResponseEntity with the purchase order details.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('READ_PO')")
    public ResponseEntity<ApiResponseDto<PurchaseOrderResponseDTO>> getPurchaseOrderById(UUID id) {
        log.info("Retrieving purchase order with ID: {}", id);
        return repository.findById(id)
                .map(purchaseOrder -> ApiResponseDto.ok(isManager() ?
                        (PurchaseOrderResponseDTO) mapper.toDetailDTO(purchaseOrder) :
                        mapper.toBasicDTO(purchaseOrder)))
                .orElseThrow(() -> new EntityNotFoundException("Purchase Order not found with ID: " + id));
    }

    /**
     * Gets all purchase orders.
     *
     * @return ResponseEntity containing a list of all purchase orders.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE') and hasAuthority('READ_PO')")
    public ResponseEntity<ApiResponseDto<List<PurchaseOrderResponseDTO>>> getAllPurchaseOrders() {
        return ApiResponseDto.ok(
                repository.findAll().stream()
                        .map(purchaseOrder -> isManager() ? (PurchaseOrderResponseDTO) mapper.toDetailDTO(purchaseOrder) : mapper.toBasicDTO(purchaseOrder))
                        .toList()
        );
    }

    /**
     * Updates an existing purchase order.
     *
     * @param id      the ID of the purchase order to update.
     * @param request the purchase order request DTO containing the updated details.
     * @return ResponseEntity with the updated purchase order details.
     */
    public ResponseEntity<ApiResponseDto<PurchaseOrderResponseDTO>> updatePurchaseOrder(UUID id, PurchaseOrderRequestDTO request) {
        log.info("Updating purchase order with ID: {}", id);
        PurchaseOrder target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Purchase Order not found with ID: " + id));

        mapper.updateEntityFromRequest(request, target);

        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(target)) :
                mapper.toBasicDTO(repository.save(target))
        );
    }

    /**
     * Deletes a purchase order by its ID.
     *
     * @param id the ID of the purchase order to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') and hasAuthority('WRITE_PO')")
    public ResponseEntity<ApiResponseDto<Void>> deletePurchaseOrder(UUID id) {
        log.info("Deleting purchase order with ID: {}", id);
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
