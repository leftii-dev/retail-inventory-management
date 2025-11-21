package dev.austinbarnes.retailinventorymanagement.common;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 * BaseSpecifications is a utility class that provides JPA Specifications for filtering entities
 * based on common attributes defined in BaseFilterDTO. Handles base filtering for all entities extending BaseEntity.
 */
public class BaseSpecifications {
    public static <T extends BaseEntity> Specification<T> applyBaseFilters(BaseFilterDTO filterDTO) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if(filterDTO == null) {
                return criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("active"), true));
            }

            if(filterDTO.createdAt() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("createdAt"), filterDTO.createdAt()));
            }

            if(filterDTO.createdBefore() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("createdAt"), filterDTO.createdBefore()));
            }

            if(filterDTO.createdAfter() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("createdAt"), filterDTO.createdAfter()));
            }

            if(filterDTO.modifiedAt() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("modifiedAt"), filterDTO.modifiedAt()));
            }

            if(filterDTO.modifiedBefore() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("modifiedAt"), filterDTO.modifiedBefore()));
            }

            if(filterDTO.modifiedAfter() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("modifiedAt"), filterDTO.modifiedAfter()));
            }

            if(filterDTO.createdBy() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("createdBy"), filterDTO.createdBy()));
            }

            if(filterDTO.modifiedBy() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("modifiedBy"), filterDTO.modifiedBy()));
            }

            if(filterDTO.showInactive() == null || !filterDTO.showInactive()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("active"), true));
            }

            return predicate;
        };
    }
}
