package dev.austinbarnes.retailinventorymanagement.inventory.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemFIlterDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucher;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucherItem;
import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class ReceivingVoucherItemSpecifications {
    public static Specification<ReceivingVoucherItem> applyFilters(ReceivingVoucherItemFIlterDTO filterDTO) {
        return (Root<ReceivingVoucherItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<ReceivingVoucherItem> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if (filterDTO != null) {
                if (filterDTO.qty() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("qty"), filterDTO.qty()));
                }

                if (filterDTO.qtyBelow() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("qty"), filterDTO.qtyBelow()));
                }

                if (filterDTO.qtyAbove() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("qty"), filterDTO.qtyAbove()));
                }

                if (filterDTO.costTotal() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("costTotal"), filterDTO.costTotal()));
                }

                if (filterDTO.costTotalBelow() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("costTotal"), filterDTO.costTotalBelow()));
                }

                if (filterDTO.costTotalAbove() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("costTotal"), filterDTO.costTotalAbove()));
                }

                if (filterDTO.product() != null) {
                    Join<ReceivingVoucherItem, Product> productJoin = root.join("product", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(productJoin.get("id"), filterDTO.product()));
                }

                if (filterDTO.receivingVoucher() != null) {
                    Join<ReceivingVoucherItem, ReceivingVoucher> rvJoin = root.join("receivingVoucher", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(rvJoin.get("id"), filterDTO.receivingVoucher()));
                }
            }
            return predicate;
        };
    }
}
