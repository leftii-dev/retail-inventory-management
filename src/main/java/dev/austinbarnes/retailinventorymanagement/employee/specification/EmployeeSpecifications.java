package dev.austinbarnes.retailinventorymanagement.employee.specification;

import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeFilterDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeePermission;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Permission;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;


public class EmployeeSpecifications {
    public static Specification<Employee> applyFilters(EmployeeFilterDTO filterDTO){
        // Automatically applies default base filters if filterDTO is null
        if(filterDTO == null){
            return (Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
                Predicate predicates = criteriaBuilder.conjunction();
                Specification<Employee> baseSpec = BaseSpecifications.applyBaseFilters(null);
                predicates.getExpressions().add(baseSpec.toPredicate(root, query, criteriaBuilder));
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("isCurrentEmployee"), true));

                return predicates;
            };
        }

        return (Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Specification<Employee> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO.baseFilterDTO());
            Predicate predicates = criteriaBuilder.conjunction();
            predicates.getExpressions().add(baseSpec.toPredicate(root, query, criteriaBuilder));

            if(filterDTO.user() != null){
                Join<Employee, User> userJoin = root.join("user");
                predicates.getExpressions().add(criteriaBuilder.equal(userJoin.get("id"), filterDTO.user()));
            }

            if(filterDTO.firstNameContains() != null){
                predicates.getExpressions().add(criteriaBuilder.like(root.get("firstName"), "%" + filterDTO.firstNameContains() + "%"));
            }

            if(filterDTO.lastNameContains() != null){
                predicates.getExpressions().add(criteriaBuilder.like(root.get("lastName"), "%" + filterDTO.lastNameContains() + "%"));
            }

            if(filterDTO.emailContains() != null){
                predicates.getExpressions().add(criteriaBuilder.like(root.get("email"), "%" + filterDTO.emailContains() + "%"));
            }

            if(filterDTO.employeeCodeContains() != null){
                predicates.getExpressions().add(criteriaBuilder.like(root.get("employeeCode"), "%" + filterDTO.employeeCodeContains() + "%"));
            }

            if(filterDTO.showNonCurrentEmployee() != null && !filterDTO.showNonCurrentEmployee()){
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("isCurrentEmployee"), true));
            }

            if(filterDTO.hasPermission() != null){
                Join<Employee, EmployeePermission> employeePermissionJoin = root.join("employeePermissions");
                Join<EmployeePermission, Permission> permissionJoin = employeePermissionJoin.join("permission");
                predicates.getExpressions().add(criteriaBuilder.like(permissionJoin.get("name"), "%" + filterDTO.hasPermission() + "%"));
            }

            return predicates;
        };
    }
}
