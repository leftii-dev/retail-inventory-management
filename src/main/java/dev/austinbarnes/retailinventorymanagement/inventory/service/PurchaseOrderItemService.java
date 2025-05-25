package dev.austinbarnes.retailinventorymanagement.inventory.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrderItem;
import dev.austinbarnes.retailinventorymanagement.inventory.mapper.PurchaseOrderItemMapper;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.PurchaseOrderItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * PurchaseOrderItemService handles all operations related to purchase order items.
 * It provides methods to create, update, retrieve, and delete purchase order items.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseOrderItemService {
    private final PurchaseOrderItemRepository repository;
    private final PurchaseOrderItemMapper mapper;

    /**
     * Creates a new purchase order item.
     *
     * @param request The purchase order item request DTO containing item details.
     * @return ResponseEntity with the created purchase order item details.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_PO')")
    public ResponseEntity<ApiResponseDto<PurchaseOrderItemResponseDTO>> createPurchaseOrderItem(
            PurchaseOrderItemRequestDTO request) {

        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(mapper.toEntity(request)))
                :
                mapper.toBasicDTO(repository.save(mapper.toEntity(request)))
        );
    }

    /**
     * Retrieves a purchase order item by its ID.
     *
     * @param id The UUID of the purchase order item.
     * @return ResponseEntity with the purchase order item details.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('READ_PO')")
    public ResponseEntity<ApiResponseDto<PurchaseOrderItemResponseDTO>> getPurchaseOrderItem(UUID id) {
        return repository.findById(id)
                .map(item -> ApiResponseDto.ok(isManager()
                        ?
                        (PurchaseOrderItemResponseDTO) mapper.toDetailDTO(item)
                        :
                        mapper.toBasicDTO(item)))
                .orElseThrow(() -> new EntityNotFoundException("Purchase Order Item not found"));
    }

    /**
     * Retrieves all purchase order items.
     *
     * @return ResponseEntity with a list of purchase order item details.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('READ_PO')")
    public ResponseEntity<ApiResponseDto<List<PurchaseOrderItemResponseDTO>>> getAllPurchaseOrderItems() {
        List<PurchaseOrderItemResponseDTO> items = repository.findAll().stream()
                .map(item -> isManager()
                        ?
                        (PurchaseOrderItemResponseDTO) mapper.toDetailDTO(item)
                        :
                        mapper.toBasicDTO(item))
                .toList();
        return ApiResponseDto.ok(items);
    }

    /**
     * Updates an existing purchase order item.
     *
     * @param id      The UUID of the purchase order item to update.
     * @param request The request DTO containing updated purchase order item details.
     * @return ResponseEntity with the updated purchase order item details.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_PO')")
    public ResponseEntity<ApiResponseDto<PurchaseOrderItemResponseDTO>> updatePurchaseOrderItem(UUID id, PurchaseOrderItemRequestDTO request) {
        PurchaseOrderItem target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Purchase Order Item not found"));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(target))
                :
                mapper.toBasicDTO(repository.save(target)));
    }

    /**
     * Deletes a purchase order item by its ID.
     *
     * @param id The UUID of the purchase order item to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_PO')")
    public ResponseEntity<ApiResponseDto<Void>> deletePurchaseOrderItem(UUID id) {
        repository.deleteById(id);
        return ApiResponseDto.noContent();
    }

    /**
     * Checks if the current user has manager or admin role.
     *
     * @return true if the user is a manager or admin, false otherwise.
     */
    private boolean isManager() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals("ROLE_MANAGER") || authority.getAuthority().equals("ROLE_ADMIN")
        );
    }
}
