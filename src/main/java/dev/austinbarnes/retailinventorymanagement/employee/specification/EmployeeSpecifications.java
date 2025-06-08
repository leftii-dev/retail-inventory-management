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
        return (Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<Employee> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if(filterDTO != null) {
                if (filterDTO.user() != null) {
                    Join<Employee, User> userJoin = root.join("user");
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(userJoin.get("id"), filterDTO.user()));
                }

                if (filterDTO.firstNameContains() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("nameFirst")), "%" + filterDTO.firstNameContains().toLowerCase() + "%"));
                }

                if (filterDTO.lastNameContains() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("nameLast")), "%" + filterDTO.lastNameContains().toLowerCase() + "%"));
                }

                if (filterDTO.emailContains() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("email"), "%" + filterDTO.emailContains() + "%"));
                }

                if (filterDTO.employeeCodeContains() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("employeeCode"), "%" + filterDTO.employeeCodeContains() + "%"));
                }

                if (filterDTO.showNonCurrentEmployee() != null && !filterDTO.showNonCurrentEmployee()) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isCurrentEmployee"), true));
                }

                if (filterDTO.hasPermission() != null) {
                    Join<Employee, EmployeePermission> employeePermissionJoin = root.join("employeePermissions");
                    Join<EmployeePermission, Permission> permissionJoin = employeePermissionJoin.join("permission");
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(permissionJoin.get("name"), "%" + filterDTO.hasPermission() + "%"));
                }
            }
            return predicate;
        };
    }
}
