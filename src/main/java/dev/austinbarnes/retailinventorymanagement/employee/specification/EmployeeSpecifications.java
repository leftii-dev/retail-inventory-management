package dev.austinbarnes.retailinventorymanagement.employee.specification;

import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeFilterDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
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
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + filterDTO.emailContains().toLowerCase() + "%"));
                }

                if (filterDTO.employeeCodeContains() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("employeeCode")), "%" + filterDTO.employeeCodeContains().toLowerCase() + "%"));
                }

                if (filterDTO.showNonCurrentEmployee() != null && !filterDTO.showNonCurrentEmployee()) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isCurrentEmployee"), true));
                }

                if (filterDTO.hasPermission() != null) {
                    Join<Employee, Permission> permissionJoin = root.join("permission");
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(permissionJoin.get("name")), "%" + filterDTO.hasPermission().toLowerCase() + "%"));
                }
            }
            return predicate;
        };
    }
}
