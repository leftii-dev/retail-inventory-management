package dev.austinbarnes.retailinventorymanagement.location.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.location.dto.details.LocationDetailsFilterDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationDetails;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class LocationDetailsSpecifications {
    public static Specification<LocationDetails> applyFilters(LocationDetailsFilterDTO filterDTO) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<LocationDetails> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if (filterDTO != null) {
                if (filterDTO.addressContains() != null) {
                    var addressPredicate = criteriaBuilder.disjunction();
                    addressPredicate = criteriaBuilder.or(addressPredicate, criteriaBuilder.like(root.get("addressLine1"), "%" + filterDTO.addressContains() + "%"));
                    addressPredicate = criteriaBuilder.or(addressPredicate, criteriaBuilder.like(root.get("addressLine2"), "%" + filterDTO.addressContains() + "%"));
                    addressPredicate = criteriaBuilder.or(addressPredicate, criteriaBuilder.like(root.get("city"), "%" + filterDTO.addressContains() + "%"));
                    addressPredicate = criteriaBuilder.or(addressPredicate, criteriaBuilder.like(root.get("state"), "%" + filterDTO.addressContains() + "%"));
                    addressPredicate = criteriaBuilder.or(addressPredicate, criteriaBuilder.like(root.get("zip"), "%" + filterDTO.addressContains() + "%"));
                    predicate = criteriaBuilder.and(predicate, addressPredicate);
                }

                if (filterDTO.phoneContains() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("phone"), "%" + filterDTO.phoneContains() + "%"));
                }

                if (filterDTO.emailContains() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("email"), "%" + filterDTO.emailContains() + "%"));
                }

                if (filterDTO.notesContains() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("notes"), "%" + filterDTO.notesContains() + "%"));
                }

                if (filterDTO.location() != null) {
                    Join<LocationDetails, Location> locationJoin = root.join("location", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(locationJoin.get("id"), filterDTO.location()));
                }

                if (filterDTO.manager() != null) {
                    Join<LocationDetails, Employee> managerJoin = root.join("manager", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(managerJoin.get("id"), filterDTO.manager()));
                }
            }
            return predicate;
        };
    }
}
