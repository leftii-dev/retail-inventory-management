package dev.austinbarnes.retailinventorymanagement.employee.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyFilterDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeeHierarchy;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeHierarchySpecifications {
    public static Specification<EmployeeHierarchy> applyFilters(EmployeeHierarchyFilterDTO filterDTO){
        // Automatically adds default base filters id filterDTO is null
        if(filterDTO == null){
            return (Root<EmployeeHierarchy> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
                Predicate predicates = criteriaBuilder.conjunction();

                Specification<EmployeeHierarchy> baseSpec = BaseSpecifications.applyBaseFilters(null);
                predicates.getExpressions().add(baseSpec.toPredicate(root, query, criteriaBuilder));
                return predicates;
            };
        }

        return (Root<EmployeeHierarchy> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicates = criteriaBuilder.conjunction();

            Specification<EmployeeHierarchy> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO.baseFilterDTO());
            predicates.getExpressions().add(baseSpec.toPredicate(root, query, criteriaBuilder));

            if(filterDTO.employeeID() != null)
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("employeeID"), filterDTO.employeeID()));

            if(filterDTO.managerID() != null)
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("managerID"), filterDTO.managerID()));
            return predicates;
        };
    }
}
