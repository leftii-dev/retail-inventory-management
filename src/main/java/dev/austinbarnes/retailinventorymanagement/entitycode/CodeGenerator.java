package dev.austinbarnes.retailinventorymanagement.entitycode;

import dev.austinbarnes.retailinventorymanagement.entitycode.entity.CodeEntity;
import dev.austinbarnes.retailinventorymanagement.entitycode.repo.CodeEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * CodeGenerator is a service class responsible for generating unique codes for various entities
 * in the retail inventory management system.
 * <p>
 * It uses a repository to fetch and update the current code index for each entity type.
 */
@Service
@RequiredArgsConstructor
public class CodeGenerator {

    private final CodeEntityRepository codeEntityRepository;

    /**
     * Retrieves the current code index for a given entity name, increments it, and saves the updated
     * value back to the repository.
     *
     * @param name The name of the entity for which to generate a code.
     * @return The new code index after incrementing.
     */
    private int getCodeIndex(String name){
        CodeEntity code = codeEntityRepository.findByName(name).orElseThrow();
        int currentIndex = code.getCode();
        currentIndex++;
        code.setCode(currentIndex);
        codeEntityRepository.save(code);
        return currentIndex;
    }

    /**
     * Generates a unique code for a new employee.
     *
     * @return A formatted string representing the new employee code.
     */
    public String generateEmployeeCode() {
        return String.format("EMP-1%05d", getCodeIndex("employee"));
    }

    /**
     * Generates a unique code for a new purchase order.
     *
     * @return A formatted string representing the new purchase order code.
     */
    public String generatePurchaseOrderCode() {
        return String.format("PO-1%06d", getCodeIndex("purchaseorder"));
    }

    /**
     * Generates a unique code for a new receiving voucher.
     *
     * @return A formatted string representing the new receiving voucher code.
     */
    public String generateReceivingVoucherCode() {
        return String.format("RV-1%06d", getCodeIndex("receivingvoucher"));
    }

    /**
     * Generates a unique code for a new transfer.
     *
     * @return A formatted string representing the new transfer code.
     */
    public String generateTransferCode() {
        return String.format("TR-1%06d", getCodeIndex("transfer"));
    }

    /**
     * Generates a unique code for a new vendor.
     *
     * @return A formatted string representing the new vendor code.
     */
    public String generateVendorCode() {
        return String.format("VEN-1%05d", getCodeIndex("vendor"));
    }

    /**
     * Generates a unique code for a new retail location.
     *
     * @return A formatted string representing the new retail location code.
     */
    public String generateRetailLocationCode() {
        return String.format("RL-1%06d", getCodeIndex("retaillocation"));
    }

    /**
     * Generates a unique code for a new warehouse location.
     *
     * @return A formatted string representing the new warehouse location code.
     */
    public String generateWarehouseLocationCode() {
        return String.format("WL-1%06d", getCodeIndex("warehouselocation"));
    }

    /**
     * Generates a unique code for a new category.
     *
     * @return A formatted string representing the new category code.
     */
    public String generateCategoryCode() {
        return String.format("CAT-1%05d", getCodeIndex("category"));
    }

    /**
     * Generates a unique code for a new product.
     *
     * @return A formatted string representing the new product code.
     */
    public String generateProductCode() {
        return String.format("P-1%07d", getCodeIndex("product"));
    }
}
