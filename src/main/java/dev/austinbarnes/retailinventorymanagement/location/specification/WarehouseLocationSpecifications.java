package dev.austinbarnes.retailinventorymanagement.location.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationFilterDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.location.entity.WarehouseLocation;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class WarehouseLocationSpecifications {
    public static Specification<WarehouseLocation> applyFilters(WarehouseLocationFilterDTO filterDTO) {
        return(Root<WarehouseLocation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<WarehouseLocation> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if(filterDTO != null){
                if(filterDTO.codeContains() != null){
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("warehouseCode")),
                                    "%" + filterDTO.codeContains().toLowerCase() + "%"));
                }
                if(filterDTO.location() != null){
                    Join<WarehouseLocation, Location> locationJoin = root.join("location", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.equal(locationJoin.get("id"), filterDTO.location()));
                }
            }
            return predicate;
        };
    }
}
