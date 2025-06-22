package dev.austinbarnes.retailinventorymanagement.product.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountFilterDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Discount;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class DiscountSpecifications {
    public static Specification<Discount> applyFilters(DiscountFilterDTO filterDTO) {
        return(Root<Discount> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<Discount> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("discount"), filterDTO));

            if(filterDTO != null) {
                if(filterDTO.codeContains() != null) {
                    predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("discountCode")), "%" + filterDTO.codeContains().toLowerCase() + "%"));
                }
                if(filterDTO.query() != null) {
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
