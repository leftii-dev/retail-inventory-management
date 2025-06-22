package dev.austinbarnes.retailinventorymanagement.location.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationFilterDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.location.entity.RetailLocation;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class RetailLocationSpecifications {
    public static Specification<RetailLocation> applyFilters(RetailLocationFilterDTO filterDTO){
        return(Root<RetailLocation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<RetailLocation> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if(filterDTO != null){
                if(filterDTO.codeContains() != null){
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("retailLocationCode")),
                                    "%" + filterDTO.codeContains().toLowerCase() + "%"));
                }
                if(filterDTO.location() != null){
                    Join<RetailLocation, Location> locationJoin = root.join("location", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.equal(locationJoin.get("id"), filterDTO.location()));
                }
            }
            return predicate;
        };
    }
}
