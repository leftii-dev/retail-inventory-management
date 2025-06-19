package dev.austinbarnes.retailinventorymanagement.location.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationFilterDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationType;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class LocationSpecifications {
    public static Specification<Location> applyFilters(LocationFilterDTO filterDTO){
        return (Root<Location> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<Location> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = baseSpec.toPredicate(root, query, criteriaBuilder);

            if(filterDTO != null){
                if(filterDTO.nameContains() != null){
                    predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + filterDTO.nameContains().toLowerCase() + "%"));
                }
                if(filterDTO.locationType() != null){
                    Join<Location, LocationType> locationTypeJoin = root.join("locationType");
                    predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(locationTypeJoin.get("id"), filterDTO.locationType()));
                }
            }

            return predicate;
        };
    }
}
