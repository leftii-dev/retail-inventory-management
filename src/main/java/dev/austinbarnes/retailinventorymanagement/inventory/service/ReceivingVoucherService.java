package dev.austinbarnes.retailinventorymanagement.inventory.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.entitycode.CodeGenerator;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherFilterDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.*;
import dev.austinbarnes.retailinventorymanagement.inventory.mapper.ReceivingVoucherMapper;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.*;
import dev.austinbarnes.retailinventorymanagement.inventory.specification.ReceivingVoucherSpecifications;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.location.repo.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
public class ReceivingVoucherService {
    private final ReceivingVoucherRepository repository;
    private final ReceivingVoucherItemRepository receivingVoucherItemRepository;
    private final ReceivingVoucherItemService receivingVoucherItemService;
    private final ReceivingVoucherMapper mapper;
    private final CodeGenerator codeGenerator;
    private final InventoryRepository inventoryRepository;
    private final StatusRepository statusRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;
    private final LocationRepository locationRepository;

    /**
     * Creates a new receiving voucher.
     *
     * @param request the receiving voucher request DTO containing the details of the receiving voucher to create.
     * @return ResponseEntity with the created receiving voucher details.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_RV')")
    public ResponseEntity<ApiResponseDto<ReceivingVoucherResponseDTO>> createReceivingVoucher(
            ReceivingVoucherRequestDTO request) {
        log.info("Creating receiving voucher: {}", request);
        ReceivingVoucher newRv = mapper.toEntity(request);
        newRv.setReceivingVoucherCode(codeGenerator.generateReceivingVoucherCode());
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(newRv)) :
                mapper.toBasicDTO(repository.save(newRv))
        );
    }

    /**
     * Retrieves a receiving voucher by its ID.
     *
     * @param id the ID of the receiving voucher to retrieve.
     * @return ResponseEntity with the receiving voucher details.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('READ_RV')")
    public ResponseEntity<ApiResponseDto<ReceivingVoucherResponseDTO>> getReceivingVoucherById(UUID id) {
        log.info("Retrieving receiving voucher with ID: {}", id);
        return repository.findById(id)
                .map(receivingVoucher -> ApiResponseDto.ok(isManager() ?
                        (ReceivingVoucherResponseDTO) mapper.toDetailDTO(receivingVoucher) :
                        mapper.toBasicDTO(receivingVoucher)))
                .orElseThrow(() -> new EntityNotFoundException("Receiving Voucher not found with ID: " + id));
    }

    /**
     * Retrieves all receiving vouchers.
     *
     * @return ResponseEntity with a list of all receiving vouchers.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('READ_RV')")
    public ResponseEntity<ApiResponseDto<List<ReceivingVoucherResponseDTO>>> getAllReceivingVouchers(
            ReceivingVoucherFilterDTO filterDTO, Pageable pageable
    ) {
        log.info("Retrieving all receiving vouchers");
        Specification<ReceivingVoucher> spec = ReceivingVoucherSpecifications.applyFilters(filterDTO);
        return ApiResponseDto.ok(repository.findAll(spec, pageable).stream()
                .map(receivingVoucher -> isManager() ?
                        (ReceivingVoucherResponseDTO) mapper.toDetailDTO(receivingVoucher) :
                        mapper.toBasicDTO(receivingVoucher))
                .toList());
    }

    /**
     * Updates an existing receiving voucher.
     *
     * @param id      the ID of the receiving voucher to update.
     * @param request the receiving voucher request DTO containing the updated details.
     * @return ResponseEntity with the updated receiving voucher details.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_RV')")
    public ResponseEntity<ApiResponseDto<ReceivingVoucherResponseDTO>> updateReceivingVoucher(UUID id, ReceivingVoucherRequestDTO request) {
        log.info("Updating receiving voucher with ID: {}", id);
        ReceivingVoucher target = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Receiving Voucher not found with ID: " + id));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(isManager() ?
                mapper.toDetailDTO(repository.save(target)) :
                mapper.toBasicDTO(repository.save(target)));
    }

    /**
     * Deletes a receiving voucher by its ID.
     *
     * @param id the ID of the receiving voucher to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_RV')")
    @Transactional
    public ResponseEntity<ApiResponseDto<Void>> deleteReceivingVoucher(UUID id) {
        log.info("Deleting receiving voucher with ID: {}", id);
        List<UUID> itemIds = receivingVoucherItemRepository.findAllByReceivingVoucher_IdAndActiveTrue(id).stream()
                .map(ReceivingVoucherItem::getId)
                .toList();
        itemIds.forEach(receivingVoucherItemId ->
                receivingVoucherItemService.deleteReceivingVoucherItem(receivingVoucherItemId));
        repository.deleteById(id);
        return ApiResponseDto.noContent();
    }

    /**
     * Creates a new receiving voucher pre-populated from a purchase order.
     * RV items are created from the PO's active line items. Vendor, total cost, notes,
     * and the PO reference are copied from the PO. Status defaults to DRAFT.
     * Location defaults to the seeded "Default Warehouse" location.
     *
     * @param purchaseOrderId the ID of the purchase order to build the receiving voucher from.
     * @return ResponseEntity with the created receiving voucher details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_RV')")
    public ResponseEntity<ApiResponseDto<ReceivingVoucherResponseDTO>> createReceivingVoucherFromPurchaseOrder(
            UUID purchaseOrderId) {
        log.info("Creating receiving voucher from purchase order ID: {}", purchaseOrderId);
        PurchaseOrder po = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new EntityNotFoundException("Purchase Order not found with ID: " + purchaseOrderId));
        Location defaultLocation = locationRepository.findByName("Default Warehouse")
                .orElseThrow(() -> new EntityNotFoundException("Default Warehouse location not found"));
        ReceivingVoucher rv = new ReceivingVoucher();
        rv.setReceivingVoucherCode(codeGenerator.generateReceivingVoucherCode());
        rv.setPurchaseOrder(po);
        rv.setVendor(po.getVendor());
        rv.setTotalCost(po.getTotalCost());
        rv.setNotes(po.getNotes());
        rv.setLocation(defaultLocation);
        rv.setStatus(statusRepository.findByName("DRAFT")
                .orElseThrow(() -> new EntityNotFoundException("Status 'DRAFT' not found")));
        ReceivingVoucher savedRv = repository.save(rv);
        List<PurchaseOrderItem> poItems =
                purchaseOrderItemRepository.findAllByPurchaseOrder_IdAndActiveTrue(purchaseOrderId);
        for (PurchaseOrderItem poi : poItems) {
            ReceivingVoucherItem rvi = new ReceivingVoucherItem();
            rvi.setReceivingVoucher(savedRv);
            rvi.setProduct(poi.getProduct());
            rvi.setQuantity(poi.getQuantity());
            rvi.setCostUnit(poi.getCostUnit());
            rvi.setCostLineTotal(poi.getCostLineTotal());
            receivingVoucherItemRepository.save(rvi);
        }
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(savedRv) :
                mapper.toBasicDTO(savedRv));
    }

    /**
     * Finalizes a receiving voucher by incrementing or creating inventory records
     * for each item at the voucher's receiving location.
     *
     * @param id the ID of the receiving voucher to finalize.
     * @return ResponseEntity with the updated receiving voucher details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN') and hasAuthority('WRITE_RV')")
    public ResponseEntity<ApiResponseDto<ReceivingVoucherResponseDTO>> finalizeReceivingVoucher(UUID id) {
        log.info("Finalizing receiving voucher with ID: {}", id);
        ReceivingVoucher rv = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Receiving Voucher not found with ID: " + id));
        String statusName = rv.getStatus().getName();
        if ("COMPLETED".equals(statusName) || "CANCELLED".equals(statusName)) {
            throw new IllegalStateException(
                    "Receiving voucher cannot be finalized: current status is " + statusName);
        }
        List<ReceivingVoucherItem> items =
                receivingVoucherItemRepository.findAllByReceivingVoucher_IdAndActiveTrue(id);
        for (ReceivingVoucherItem item : items) {
            inventoryRepository
                    .findByProductIdAndLocationIdAndActiveTrue(item.getProduct().getId(), rv.getLocation().getId())
                    .ifPresentOrElse(
                            inv -> {
                                inv.setQuantity(inv.getQuantity() + item.getQuantity());
                                inventoryRepository.save(inv);
                            },
                            () -> {
                                Inventory newInventory = new Inventory();
                                newInventory.setProduct(item.getProduct());
                                newInventory.setLocation(rv.getLocation());
                                newInventory.setQuantity(item.getQuantity());
                                inventoryRepository.save(newInventory);
                            }
                    );
        }
        rv.setStatus(statusRepository.findByName("COMPLETED")
                .orElseThrow(() -> new EntityNotFoundException("Status 'COMPLETED' not found")));
        repository.save(rv);
        return ApiResponseDto.ok(isManager() ?
                (ReceivingVoucherResponseDTO) mapper.toDetailDTO(rv) :
                mapper.toBasicDTO(rv));
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
