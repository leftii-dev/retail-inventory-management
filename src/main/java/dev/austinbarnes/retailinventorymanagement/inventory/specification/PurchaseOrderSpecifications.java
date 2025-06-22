package dev.austinbarnes.retailinventorymanagement.inventory.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderFilterDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrder;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Status;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Vendor;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class PurchaseOrderSpecifications {
    public static Specification<PurchaseOrder> applyFilters(PurchaseOrderFilterDTO filterDTO) {
        return(Root<PurchaseOrder> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<PurchaseOrder> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if(filterDTO != null) {
                if(filterDTO.codeContains() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("purchaseOrderCode")), "%" + filterDTO.codeContains() + "%"));
                }

                if(filterDTO.dateExpected() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dateExpected"), filterDTO.dateExpected()));
                }

                if(filterDTO.totalLessThan() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("totalCost"), filterDTO.totalLessThan()));
                }

                if(filterDTO.totalGreaterThan() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("totalCost"), filterDTO.totalGreaterThan()));
                }

                if(filterDTO.vendor() != null){
                    Join<PurchaseOrder, Vendor> vendorJoin = root.join("vendor", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(vendorJoin.get("id"), filterDTO.vendor()));
                }

                if(filterDTO.status() != null){
                    Join<PurchaseOrder, Status> statusJoin = root.join("status", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(statusJoin.get("id"), filterDTO.status()));
                }
            }
            return predicate;
        };
    }
}
