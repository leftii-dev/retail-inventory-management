package dev.austinbarnes.retailinventorymanagement.product.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_image")
@ToString(callSuper = true)
public class ProductImage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    private Product product;

    @Column(name = "image_url", nullable = false)
    @NotNull
    @Size(max = 500)
    private String imageUrl;

    @Column(name = "display_order", nullable = false)
    @NotNull
    @Min(0)
    private Integer displayOrder;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    @Column(name = "alt_text")
    @Size(max = 255)
    private String altText;

    @Column(name = "image_type")
    @Size(max = 50)
    private String imageType; // thumbnail, display, etc.

    @Column(name = "blur_data_url", length = 10000)
    @Size(max = 10000)
    private String blurDataUrl;
}
