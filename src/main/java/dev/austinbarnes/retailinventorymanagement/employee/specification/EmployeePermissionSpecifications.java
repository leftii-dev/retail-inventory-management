package dev.austinbarnes.retailinventorymanagement.employee.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.EmployeePermissionFilterDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeePermission;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Permission;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;


public class EmployeePermissionSpecifications {
    public static Specification<EmployeePermission> applyFilters(EmployeePermissionFilterDTO filterDTO) {
        return(Root<EmployeePermission> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<EmployeePermission> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));

            if(filterDTO != null) {
                if(filterDTO.employeeId() != null) {
                    Join<EmployeePermission, Employee> join = root.join("employee", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(join.get("id"), filterDTO.employeeId()));
                }

                if(filterDTO.permissionId() != null) {
                    Join<EmployeePermission, Permission> join = root.join("permission", JoinType.INNER);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(join.get("id"), filterDTO.permissionId()));
                }
            }
            return predicate;
        };
    }
}
