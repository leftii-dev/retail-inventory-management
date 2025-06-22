package dev.austinbarnes.retailinventorymanagement.inventory.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemFilterDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrder;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrderItem;
import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;



public class PurchaseOrderItemSpecifications {
    public static Specification<PurchaseOrderItem> applyFilters(PurchaseOrderItemFilterDTO filterDTO) {
        return (Root<PurchaseOrderItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<PurchaseOrderItem> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if(filterDTO != null){
                if(filterDTO.unitCostEqual() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("costUnit"), filterDTO.unitCostEqual()));
                }

                if(filterDTO.unitCostBelow() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("costUnit"), filterDTO.unitCostBelow()));
                }

                if(filterDTO.unitCostAbove() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("costUnit"), filterDTO.unitCostAbove()));
                }

                if(filterDTO.totalCostEqual() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("costLineTotal"), filterDTO.totalCostEqual()));
                }

                if(filterDTO.totalCostBelow() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("costLineTotal"), filterDTO.totalCostBelow()));
                }

                if(filterDTO.totalCostAbove() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("costLineTotal"), filterDTO.totalCostAbove()));
                }

                if(filterDTO.qtyEqual() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("quantity"), filterDTO.qtyEqual()));
                }

                if(filterDTO.qtyBelow() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("quantity"), filterDTO.qtyBelow()));
                }

                if(filterDTO.qtyAbove() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("quantity"), filterDTO.qtyAbove()));
                }

                if(filterDTO.purchaseOrder() != null){
                    Join<PurchaseOrderItem, PurchaseOrder> poJoin = root.join("purchaseOrder", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(poJoin.get("id"), filterDTO.purchaseOrder()));
                }

                if(filterDTO.product() != null){
                    Join<PurchaseOrderItem, Product> productJoin = root.join("product", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(productJoin.get("id"), filterDTO.product()));
                }
            }
            return predicate;
        };
    }
}
