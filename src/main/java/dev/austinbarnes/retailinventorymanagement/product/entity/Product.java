package dev.austinbarnes.retailinventorymanagement.product.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Product is an entity class that represents a product in the retail inventory management system.
 * It includes fields for the unique identifier, SKU, product code, name, description, cost, price, weight,
 * dimensions, additional details, and relationships with category, brand, and discount.
 * This class is used to map to the "product" table in the database.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
@ToString(callSuper = true, exclude = {"category", "brand", "discount"})
public class Product extends BaseEntity {
    @Column(name = "sku", nullable = false, unique = true)
    @Size(max = 20, message = "Sku cannot exceed 20 characters")
    @NotNull
    private String sku;

    @Column(name = "product_code", nullable = false, updatable = false, unique = true)
    private String productCode;

    @Column(name = "name", nullable = false)
    @NotNull
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters")
    private String name;

    @Column(name = "description")
    @Size(max = 3000, message = "Description cannot exceed 3000 characters")
    private String description;

    @Column(name = "cost", precision = 12, scale = 2)
    @DecimalMax(value = "9999999999.99", message = "Cost cannot exceed $9,999,999,999.99")
    @DecimalMin(value = "0.01", message = "Cost cannot be less than $0.01")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal cost;

    @Column(name = "price", precision = 12, scale = 2)
    @DecimalMax(value = "9999999999.99", message = "Price cannot exceed $9,999,999,999.99")
    @DecimalMin(value = "0.01", message = "Price cannot be less than $0.01")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    @Column(name = "weight", precision = 8, scale = 2)
    @DecimalMax(value = "999999.99", message = "Weight cannot exceed 999,999.99")
    @DecimalMin(value = "0.00", message = "Weight cannot be negative")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal weight;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dimensions", columnDefinition = "jsonb")
    private Map<String, Object> dimensions;

    // For attributes that may not apply to all items (Size, Color, Box Counts, etc.)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "additional_details", columnDefinition = "jsonb")
    private Map<String, Object> additionalDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @Valid
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @Valid
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    @Valid
    Discount discount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<ProductImage> images = new ArrayList<>();

    public void setDefaultImage(ProductImage newDefault){
        if (!images.contains(newDefault)){
            throw new IllegalArgumentException("Image does not belong to this product.");
        }

        images.forEach(img -> img.setIsDefault(false));
        newDefault.setIsDefault(true);
    }

    public ProductImage getDefaultImage() {
        return images.stream()
                .filter(ProductImage::getIsDefault)
                .findFirst()
                .orElse(images.isEmpty() ? null : images.getFirst());
    }

    public void addImage(ProductImage image) {
        images.add(image);
        image.setProduct(this);

        if(images.size() == 1) {
            image.setIsDefault(true);
        }
    }

    public void removeImage(ProductImage image) {
        images.remove(image);
        image.setProduct(null);

        if(image.getIsDefault() && !images.isEmpty()) {
            images.getFirst().setIsDefault(true);
        }
    }
}
