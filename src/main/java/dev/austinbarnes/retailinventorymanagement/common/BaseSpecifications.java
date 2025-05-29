package dev.austinbarnes.retailinventorymanagement.common;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class BaseSpecifications {
    public static <T extends BaseEntity> Specification<T> applyBaseFilters(BaseFilterDTO filterDTO) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicates = criteriaBuilder.conjunction();

            if(filterDTO.createdAt() != null)
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("createdAt"), filterDTO.createdAt()));

            if(filterDTO.createdBefore() != null)
                predicates.getExpressions().add(criteriaBuilder.lessThan(root.get("createdAt"), filterDTO.createdBefore()));

            if(filterDTO.createdAfter() != null)
                predicates.getExpressions().add(criteriaBuilder.greaterThan(root.get("createdAt"), filterDTO.createdAfter()));

            if(filterDTO.modifiedAt() != null)
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("modifiedAt"), filterDTO.modifiedAt()));

            if(filterDTO.modifiedBefore() != null)
                predicates.getExpressions().add(criteriaBuilder.lessThan(root.get("modifiedAt"), filterDTO.modifiedBefore()));

            if(filterDTO.modifiedAfter() != null)
                predicates.getExpressions().add(criteriaBuilder.greaterThan(root.get("modifiedAt"), filterDTO.modifiedAfter()));

            if(filterDTO.createdBy() != null)
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("createdBy"), filterDTO.createdBy()));

            if(filterDTO.modifiedBy() != null)
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("modifiedBy"), filterDTO.modifiedBy()));

            if (filterDTO.showInactive())
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("active"), true));

            return predicates;
        };
    }
}
