package dev.austinbarnes.retailinventorymanagement.inventory.specification;

import dev.austinbarnes.retailinventorymanagement.common.BaseSpecifications;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferFilterDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Transfer;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class TransferSpecifications {
    public static Specification<Transfer> applyFilters(TransferFilterDTO filterDTO) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            Specification<Transfer> baseSpec = BaseSpecifications.applyBaseFilters(filterDTO != null ? filterDTO.baseFilterDTO() : null);
            predicate = criteriaBuilder.and(predicate, baseSpec.toPredicate(root, query, criteriaBuilder));


            if (filterDTO != null) {
                if(filterDTO.transferDate() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("date"), filterDTO.transferDate()));
                }
                if(filterDTO.transferDateBefore() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("date"), filterDTO.transferDateBefore()));
                }
                if(filterDTO.transferDateAfter() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("date"), filterDTO.transferDateAfter()));
                }
                if(filterDTO.codeContains() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("transferCode")), "%" + filterDTO.codeContains().toLowerCase() + "%"));
                }
                if(filterDTO.totalCost() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("totalCost"), filterDTO.totalCost()));
                }
                if(filterDTO.totalCostBelow() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("totalCost"), filterDTO.totalCostBelow()));
                }
                if(filterDTO.totalCostAbove() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("totalCost"), filterDTO.totalCostAbove()));
                }
                if(filterDTO.qty() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("totalQuantity"), filterDTO.qty()));
                }
                if(filterDTO.qtyBelow() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("totalQuantity"), filterDTO.qtyBelow()));
                }
                if(filterDTO.qtyAbove() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("totalQuantity"), filterDTO.qtyAbove()));
                }
                if (filterDTO.transferTo() != null) {
                    Join<Transfer, Location> transferToJoin = root.join("transferTo");
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(transferToJoin.get("id"), filterDTO.transferTo()));
                }
                if (filterDTO.transferFrom() != null) {
                    Join<Transfer, Location> transferFromJoin = root.join("transferFrom");
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(transferFromJoin.get("id"), filterDTO.transferFrom()));
                }
            }

            return predicate;
        };
    }
}
