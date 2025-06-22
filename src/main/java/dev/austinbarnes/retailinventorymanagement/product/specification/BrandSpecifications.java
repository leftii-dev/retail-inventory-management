package dev.austinbarnes.retailinventorymanagement.product.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandFilterDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Brand;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class BrandSpecifications {
    public static Specification<Brand> applyFilters(BrandFilterDTO filterDTO
    ) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<Brand> baseSpec =  BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if(filterDTO != null) {
                if (filterDTO.query() != null) {
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.or(
                                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + filterDTO.query().toLowerCase() + "%"),
                                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + filterDTO.query().toLowerCase() + "%")
                            )
                    );
                }
            }
            return predicate;
        };
    }
}
