package dev.austinbarnes.retailinventorymanagement.entitycode;

import dev.austinbarnes.retailinventorymanagement.entitycode.entity.CodeEntity;
import dev.austinbarnes.retailinventorymanagement.entitycode.repo.CodeEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeGenerator {

    private final CodeEntityRepository codeEntityRepository;

    private int getCodeIndex(String name){
        CodeEntity code = codeEntityRepository.findByName(name).orElseThrow();
        int currentIndex = code.getCode();
        currentIndex++;
        code.setCode(currentIndex);
        codeEntityRepository.save(code);
        return currentIndex;
    }

    public String generateEmployeeCode() {
        return String.format("EMP-1%05d", getCodeIndex("employee"));
    }

    public String generatePurchaseOrderCode() {
        return String.format("PO-1%06d", getCodeIndex("purchaseorder"));
    }

    public String generateReceivingVoucherCode() {
        return String.format("RV-1%06d", getCodeIndex("receivingvoucher"));
    }

    public String generateTransferCode() {
        return String.format("TR-1%06d", getCodeIndex("transfer"));
    }

    public String generateVendorCode() {
        return String.format("VEN-1%05d", getCodeIndex("vendor"));
    }

    public String generateRetailLocationCode() {
        return String.format("RL-1%06d", getCodeIndex("retaillocation"));
    }

    public String generateWarehouseLocationCode() {
        return String.format("WL-1%06d", getCodeIndex("warehouselocation"));
    }

    public String generateCategoryCode() {
        return String.format("CAT-1%05d", getCodeIndex("category"));
    }

    public String generateProductCode() {
        return String.format("P-1%07d", getCodeIndex("product"));
    }
}
