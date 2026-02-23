package dev.austinbarnes.retailinventorymanagement.inventory.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Vendor is an entity class representing a vendor in a retail management system.
 * <p>
 * It contains fields for the vendor code, name, address, contact information, and email.
 * <p>
 * The class uses JPA annotations to map the entity to a database table and includes validation annotations
 * to ensure that the fields are within specified ranges and constraints.
 */
@Entity
@Table(name = "vendor")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
public class Vendor extends BaseEntity {
    @Column(name = "vendor_code", nullable = false, updatable = false, unique = true)
    @NotNull
    private String vendorCode;

    @Column(name = "name")
    @Size(min = 2, max = 100, message = "Vendor name must be between 2 and 100 characters")
    private String name;

    @Column(name = "address_line_1")
    @Size(min = 3, max = 60, message = "Address line 1 must be between 3 and 60 characters")
    private String addressLine1;

    @Column(name = "address_line_2")
    @Size(min = 3, max = 60, message = "Address line 2 must be between 3 and 60 characters")
    private String addressLine2;

    @Column(name = "city")
    @Size(min = 2, max = 30, message = "City must be between 2 and 30 characters")
    private String city;

    @Column(name = "state")
    @Size(min = 2, max = 2, message = "State must be a 2-character abbreviation")
    private String state;

    @Column(name = "zip_code")
    @Pattern(regexp = "\\d{5}", message = "Zip Code must be exactly 5 digits long")
    private String zipCode;

    @Column(name = "contact_name")
    @Size(min = 2, max = 60, message = "Contact name must be between 2 and 60 characters")
    private String contactName;

    @Column(name = "phone")
    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits, no spaces or hyphens")
    private String phone;

    @Column(name = "email")
    @Email(message = "Invalid email format")
    @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters")
    private String email;
}
