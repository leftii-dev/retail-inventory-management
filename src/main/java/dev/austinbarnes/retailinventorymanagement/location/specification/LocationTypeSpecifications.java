package dev.austinbarnes.retailinventorymanagement.location.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeFilterDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class LocationTypeSpecifications {
    public static Specification<LocationType> applyFilters(LocationTypeFilterDTO filterDTO){
        return(Root<LocationType> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<LocationType> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if(filterDTO != null){
                if(filterDTO.nameContains() != null){
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + filterDTO.nameContains().toLowerCase() + "%"));
                }
            }
            return predicate;
        };
    }
}
