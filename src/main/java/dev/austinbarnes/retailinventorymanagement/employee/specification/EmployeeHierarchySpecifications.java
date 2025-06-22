package dev.austinbarnes.retailinventorymanagement.employee.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyFilterDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeeHierarchy;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeHierarchySpecifications {
    public static Specification<EmployeeHierarchy> applyFilters(EmployeeHierarchyFilterDTO filterDTO){

        return (Root<EmployeeHierarchy> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicates = criteriaBuilder.conjunction();

            Specification<EmployeeHierarchy> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO.baseFilterDTO());
            predicates = criteriaBuilder.and(baseSpec.toPredicate(root, query, criteriaBuilder));

            if(filterDTO.employeeID() != null){
                Join<EmployeeHierarchy, Employee> join = root.join("employee", JoinType.INNER);
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(join.get("id"), filterDTO.employeeID()));
            }

            if(filterDTO.managerID() != null){
                Join<EmployeeHierarchy, Employee> join = root.join("manager", JoinType.INNER);
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(join.get("id"), filterDTO.managerID()));
            }
            return predicates;
        };
    }
}
