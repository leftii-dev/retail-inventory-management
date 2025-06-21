package dev.austinbarnes.retailinventorymanagement.location.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.location.dto.hours.LocationHoursFilterDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationHours;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class LocationHoursSpecifications {
    public static Specification<LocationHours> applyFilter(LocationHoursFilterDTO filterDTO){
        return (Root<LocationHours> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<LocationHours> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if(filterDTO != null) {
                if (filterDTO.location() != null) {
                    Join<LocationHours, Location> locationJoin = root.join("location", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(locationJoin.get("id"), filterDTO.location()));
                }
            }
            return predicate;
        };
    }
}
