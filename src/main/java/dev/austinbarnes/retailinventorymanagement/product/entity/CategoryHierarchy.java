package dev.austinbarnes.retailinventorymanagement.product.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "category_hierarchy")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryHierarchy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @Valid
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id", referencedColumnName = "id")
    @Valid
    private Category parentCategory;

    @Column(name = "deleted", nullable = false)
    @NotNull
    private boolean deleted = false;
}
