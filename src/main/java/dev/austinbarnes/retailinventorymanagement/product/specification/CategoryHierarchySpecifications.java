package dev.austinbarnes.retailinventorymanagement.product.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyFilterDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Category;
import dev.austinbarnes.retailinventorymanagement.product.entity.CategoryHierarchy;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;


public class CategoryHierarchySpecifications {
    public static Specification<CategoryHierarchy> applyFilters(CategoryHierarchyFilterDTO filterDTO){
        return (Root<CategoryHierarchy> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<CategoryHierarchy> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if(filterDTO != null) {
                if (filterDTO.category() != null) {
                    Join<CategoryHierarchy, Category> categoryJoin = root.join("category", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(categoryJoin.get("id"), filterDTO.category()));
                }
                if (filterDTO.parentCategory() != null) {
                    Join<CategoryHierarchy, Category> parentCategoryJoin = root.join("parentCategory", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(parentCategoryJoin.get("id"), filterDTO.parentCategory()));
                }
            }
            return predicate;
        };
    }
}
