package dev.austinbarnes.retailinventorymanagement.inventory.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryFilterDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryQtyChangeRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Inventory;
import dev.austinbarnes.retailinventorymanagement.inventory.mapper.InventoryMapper;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.InventoryRepository;
import dev.austinbarnes.retailinventorymanagement.inventory.specification.InventorySpecificatitions;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * InventoryService handles all operations related to inventory management.
 * <p>
 * It provides methods to create, update, retrieve, and delete inventory records.
 */
@Service
@AllArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository repository;
    private final InventoryMapper mapper;

    /**
     * Creates a new inventory records.
     *
     * @param request The inventory request DTO containing item details.
     * @return ResponseEntity with the created inventory item details.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponseDto<InventoryResponseDTO>> createInventory(InventoryRequestDTO request) {
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(mapper.toEntity(request)))
                :
                mapper.toBasicDTO(repository.save(mapper.toEntity(request))));
    }

    /**
     * Updates an existing inventory record count.
     *
     * @param request The inventory quantity change request DTO containing item ID and quantity change.
     * @return ResponseEntity with the updated inventory item details.
     */
    public ResponseEntity<ApiResponseDto<InventoryResponseDTO>> updateInventory(InventoryQtyChangeRequestDTO request) {
        Inventory inventory = repository.findById(request.id()).orElseThrow(() -> new EntityNotFoundException("Inventory item not found"));
        inventory.setQuantity(inventory.getQuantity() + request.quantityChange());
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(inventory))
                :
                mapper.toBasicDTO(repository.save(inventory)));
    }

    /**
     * Retrieves an inventory item by its unique identifier.
     *
     * @param id The UUID of the inventory item to retrieve.
     * @return ResponseEntity containing the inventory item details.
     * @throws EntityNotFoundException if the inventory item is not found.
     */
    public ResponseEntity<ApiResponseDto<InventoryResponseDTO>> getInventoryById(UUID id) {
        Inventory target = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Inventory item not found"));
        return ApiResponseDto.ok(isManager() ? mapper.toDetailDTO(target) : mapper.toBasicDTO(target));
    }

    /**
     * Retrieves all inventory items.
     *
     * @return ResponseEntity containing a list of all inventory items.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponseDto<List<InventoryResponseDTO>>> getAllInventories(InventoryFilterDTO filterDTO, Pageable pageable) {
        Specification<Inventory> spec = InventorySpecificatitions.applyFilters(filterDTO);

        return ApiResponseDto.ok(repository.findAll(spec, pageable).stream()
                .map(inventory -> isManager() ? (InventoryResponseDTO) mapper.toDetailDTO(inventory) : mapper.toBasicDTO(inventory))
                .toList());
    }

    /**
     * Deletes an inventory item by its unique identifier.
     *
     * @param id The UUID of the inventory item to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponseDto<Void>> deleteInventory(UUID id) {
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
