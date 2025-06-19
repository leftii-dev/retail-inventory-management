package dev.austinbarnes.retailinventorymanagement.inventory.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferItemFilterDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Transfer;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.TransferItem;
import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class TransferItemSpecifications {
    public static Specification<TransferItem> applyFilters(TransferItemFilterDTO filterDTO) {
        return (Root<TransferItem> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<TransferItem> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if (filterDTO != null) {
                if (filterDTO.qty() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("quantity"), filterDTO.qty()));
                }
                if (filterDTO.qtyBelow() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("quantity"), filterDTO.qtyBelow()));
                }
                if (filterDTO.qtyAbove() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("quantity"), filterDTO.qtyAbove()));
                }
                if(filterDTO.cost() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("costUnit"), filterDTO.cost()));
                }
                if(filterDTO.costBelow() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("costUnit"), filterDTO.costBelow()));
                }
                if(filterDTO.costAbove() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("costUnit"), filterDTO.costAbove()));
                }
                if (filterDTO.transfer() != null) {
                    Join<TransferItem, Transfer> transferJoin = root.join("transfer", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(transferJoin.get("id"), filterDTO.transfer()));
                }
                if(filterDTO.product() != null) {
                    Join<TransferItem, Product> productJoin = root.join("product", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(productJoin.get("id"), filterDTO.product()));
                }
            }
            return predicate;
        };
    }
}
