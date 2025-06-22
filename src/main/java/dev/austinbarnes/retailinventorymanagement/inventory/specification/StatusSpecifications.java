package dev.austinbarnes.retailinventorymanagement.inventory.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusFilterDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Status;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class StatusSpecifications {
    public static Specification<Status> applyFilters(StatusFilterDTO filterDTO) {
        return (Root<Status> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<Status> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if (filterDTO != null) {
                if (filterDTO.search() != null && !filterDTO.search().isEmpty()) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + filterDTO.search().toLowerCase() + "%"));
                    predicate = criteriaBuilder.or(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + filterDTO.search().toLowerCase() + "%"));
                }
            }
            return predicate;
        };
    }
}
