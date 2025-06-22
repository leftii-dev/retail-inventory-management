package dev.austinbarnes.retailinventorymanagement.inventory.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherFilterDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrder;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucher;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Status;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Vendor;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class ReceivingVoucherSpecifications {
    public static Specification<ReceivingVoucher> applyFilters(ReceivingVoucherFilterDTO filterDTO) {
        return (Root<ReceivingVoucher> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<ReceivingVoucher> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO()  : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if(filterDTO != null) {
                if(filterDTO.codeContains() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("receivingVoucherCode")), "%" + filterDTO.codeContains().toLowerCase() + "%"));
                }
                if(filterDTO.freightEqual() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("freightCost"), filterDTO.freightEqual()));
                }
                if(filterDTO.freightBelow() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("freightCost"), filterDTO.freightBelow()));
                }
                if(filterDTO.freightAbove() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("freightCost"), filterDTO.freightAbove()));
                }
                if(filterDTO.feeEqual() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("feeCost"), filterDTO.feeEqual()));
                }
                if(filterDTO.feeBelow() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("feeCost"), filterDTO.feeBelow()));
                }
                if(filterDTO.feeAbove() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("feeCost"), filterDTO.feeAbove()));
                }
                if(filterDTO.totalEqual() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("totalCost"), filterDTO.totalEqual()));
                }
                if(filterDTO.totalBelow() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("totalCost"), filterDTO.totalBelow()));
                }
                if(filterDTO.totalAbove() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("totalCost"), filterDTO.totalAbove()));
                }
                if(filterDTO.discountEqual() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("discountTotal"), filterDTO.discountEqual()));
                }
                if(filterDTO.discountBelow() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("discountTotal"), filterDTO.discountBelow()));
                }
                if(filterDTO.discountAbove() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("discountTotal"), filterDTO.discountAbove()));
                }
                if(filterDTO.discountPercentEqual() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("discountPercent"), filterDTO.discountPercentEqual()));
                }
                if(filterDTO.discountPercentBelow() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("discountPercent"), filterDTO.discountPercentBelow()));
                }
                if(filterDTO.discountPercentAbove() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("discountPercent"), filterDTO.discountPercentAbove()));
                }
                if(filterDTO.discountOn() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("paymentDiscountDate"), filterDTO.discountOn()));
                }
                if(filterDTO.discountBefore() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("paymentDiscountDate"), filterDTO.discountBefore()));
                }
                if(filterDTO.discountAfter() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("paymentDiscountDate"), filterDTO.discountAfter()));
                }
                if(filterDTO.dueOn() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("paymentNetDate"), filterDTO.dueOn()));
                }
                if(filterDTO.dueBefore() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("paymentNetDate"), filterDTO.dueBefore()));
                }
                if(filterDTO.dueAfter() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("paymentNetDate"), filterDTO.dueAfter()));
                }
                if(filterDTO.notesContain() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("notes")), "%" + filterDTO.notesContain().toLowerCase() + "%"));
                }
                if(filterDTO.purchaseOrder() != null) {
                    Join<ReceivingVoucher, PurchaseOrder> purchaseOrderJoin = root.join("purchaseOrder", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(purchaseOrderJoin.get("id"), filterDTO.purchaseOrder()));
                }
                if(filterDTO.location() != null) {
                    Join<ReceivingVoucher, Location> locationJoin = root.join("location", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(locationJoin.get("id"), filterDTO.location()));
                }
                if(filterDTO.vendor() != null) {
                    Join<ReceivingVoucher, Vendor> vendorJoin = root.join("vendor", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(vendorJoin.get("id"), filterDTO.vendor()));
                }
                if(filterDTO.status() != null) {
                    Join<ReceivingVoucher, Status> statusJoin = root.join("status", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(statusJoin.get("id"), filterDTO.status()));
                }
            }
            return predicate;
        };
    }
}
