package dev.austinbarnes.retailinventorymanagement.product.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryFilterDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Category;
import dev.austinbarnes.retailinventorymanagement.product.entity.Discount;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecifications {
    public static Specification<Category> applyFilters(CategoryFilterDTO filterDTO) {
        return (Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<Category> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if(filterDTO != null){
                if(filterDTO.codeContains() != null){
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("categoryCode")),
                                    "%" + filterDTO.codeContains().toLowerCase() + "%"));
                }
                if(filterDTO.query() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(predicate,
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%" + filterDTO.query().toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),"%" + filterDTO.query().toLowerCase() + "%")
                            )
                    );
                }
                if(filterDTO.discount() != null){
                    Join<Category, Discount> discountJoin = root.join("discount", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.equal(discountJoin.get("id"), filterDTO.discount()));
                }
            }

            return predicate;
        };
    }
}
