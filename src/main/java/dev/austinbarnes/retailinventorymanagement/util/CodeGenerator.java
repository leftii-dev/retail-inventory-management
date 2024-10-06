package dev.austinbarnes.retailinventorymanagement.util;

public class CodeGenerator {
    public static String generateEmployeeCode(int currentIndex) {
        return String.format("EMP-1%05d", currentIndex + 1);
    }

    public static String generatePurchaseOrderCode(int currentIndex) {
        return String.format("PO-1%06d", currentIndex + 1);
    }

    public static String generateReceivingVoucherCode(int currentIndex) {
        return String.format("RV-1%06d", currentIndex + 1);
    }

    public static String generateTransferCode(int currentIndex) {
        return String.format("TR-1%06d", currentIndex + 1);
    }

    public static String generateVendorCode(int currentIndex) {
        return String.format("VEN-1%05d", currentIndex + 1);
    }

    public static String generateRetailLocationCode(int currentIndex) {
        return String.format("RL-1%06d", currentIndex + 1);
    }

    public static String generateWarehouseLocationCode(int currentIndex) {
        return String.format("WL-1%06d", currentIndex + 1);
    }

    public static String generateCategoryCode(int currentIndex) {
        return String.format("CAT-1%05d", currentIndex + 1);
    }

    public static String generateProductCode(int currentIndex) {
        return String.format("P-1%07d", currentIndex + 1);
    }
}
