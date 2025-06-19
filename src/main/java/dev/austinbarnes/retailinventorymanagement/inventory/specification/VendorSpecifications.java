package dev.austinbarnes.retailinventorymanagement.inventory.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorFilterDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Vendor;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class VendorSpecifications {
    public static Specification<Vendor> applyFilters(VendorFilterDTO filterDTO) {
        return (Root<Vendor> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Specification<Vendor> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));
            if(filterDTO != null) {
                if(filterDTO.codeContains() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), "%" + filterDTO.codeContains().toLowerCase() + "%"));
                }
                if(filterDTO.nameContains() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + filterDTO.nameContains().toLowerCase() + "%"));
                }
                if(filterDTO.addressContains() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + filterDTO.addressContains().toLowerCase() + "%"));
                }
                if(filterDTO.addressContains() != null) {
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.or(predicate,
                                    criteriaBuilder.like(criteriaBuilder.lower(root.get("addressLine1")), "%" + filterDTO.addressContains().toLowerCase() + "%"),
                                    criteriaBuilder.like(criteriaBuilder.lower(root.get("addressLine2")), "%" + filterDTO.addressContains().toLowerCase() + "%")
                            ));
                }
                if(filterDTO.contactContains() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("contactName")), "%" + filterDTO.contactContains().toLowerCase() + "%"));
                }
                if(filterDTO.phoneContains() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), "%" + filterDTO.phoneContains().toLowerCase() + "%"));
                }
                if(filterDTO.emailContains() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + filterDTO.emailContains().toLowerCase() + "%"));
                }
            }

            return predicate;
        };
    }
}
