package dev.austinbarnes.retailinventorymanagement.product.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

/**
 * CategoryHierarchy is an entity class that represents the hierarchy of categories in the retail inventory management system.
 * It includes fields for the category and its parent category.
 * This class is used to map to the "category_hierarchy" table in the database.
 */
@Entity
@Table(name = "category_hierarchy")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"category", "parentCategory"})
public class CategoryHierarchy extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @Valid
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id", referencedColumnName = "id")
    @Valid
    private Category parentCategory;
}
