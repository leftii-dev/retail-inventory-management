package dev.austinbarnes.retailinventorymanagement.inventory.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryFilterDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Inventory;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class InventorySpecificatitions {
    public static Specification<Inventory> applyFilters(InventoryFilterDTO filterDTO) {
        return (Root<Inventory> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<Inventory> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if(filterDTO != null) {
                if(filterDTO.qtyLessThan() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("quantity"), filterDTO.qtyLessThan()));
                }

                if(filterDTO.qtyGreaterThan() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("quantity"), filterDTO.qtyGreaterThan()));
                }

                if(filterDTO.product() != null) {
                    Join<Inventory, Product> join = root.join("product", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(join.get("id"), filterDTO.product()));
                }

                if(filterDTO.location() != null) {
                    Join<Inventory, Location> join = root.join("location", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(join.get("id"), filterDTO.location()));
                }
            }
            return predicate;
        };
    }
}
