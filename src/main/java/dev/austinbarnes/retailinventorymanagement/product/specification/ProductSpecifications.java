package dev.austinbarnes.retailinventorymanagement.product.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductFilterDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Category;
import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;


public class ProductSpecifications {
    public static Specification<Product> applyFilters(ProductFilterDTO filterDTO) {
        return(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<Product> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if(filterDTO != null){
                if(filterDTO.skuContains() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("sku"), "%" + filterDTO.skuContains() + "%"));
                }
                if(filterDTO.codeContains() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("productCode"), "%" + filterDTO.codeContains() + "%"));
                }
                if(filterDTO.query() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + filterDTO.query().toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + filterDTO.query().toLowerCase() + "%")
                    ));
                }
                if(filterDTO.costEqual() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("cost"), filterDTO.costEqual()));
                }
                if(filterDTO.costBelow() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("cost"), filterDTO.costBelow()));
                }
                if(filterDTO.costAbove() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("cost"), filterDTO.costAbove()));
                }
                if(filterDTO.priceEqual() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("price"), filterDTO.priceEqual()));
                }
                if(filterDTO.priceBelow() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("price"), filterDTO.priceBelow()));
                }
                if(filterDTO.priceAbove() != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("price"), filterDTO.priceAbove()));
                }
                if(filterDTO.category() != null){
                    Join<Product, Category> categoryJoin = root.join("category");
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(categoryJoin.get("id"), filterDTO.category()));
                }
                if(filterDTO.brand() != null){
                    Join<Product, Category> brandJoin = root.join("brand");
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(brandJoin.get("id"), filterDTO.brand()));
                }
                if(filterDTO.discount() != null){
                    Join<Product, Category> discountJoin = root.join("discount");
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(discountJoin.get("id"), filterDTO.discount()));
                }
            }
            return predicate;
        };
    }
}
