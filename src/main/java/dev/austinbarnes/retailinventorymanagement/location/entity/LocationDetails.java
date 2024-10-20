package dev.austinbarnes.retailinventorymanagement.location.entity;

import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "location_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "address_line_1")
    @Size(max = 60, message = "Address Line must be 60 characters or less")
    private String addressLine1;

    @Column(name = "address_line_2")
    @Size(max = 60, message = "Address Line must be 60 characters or less")
    private String addressLine2;

    @Column(name = "city")
    @Size(max = 30, message = "City must be 30 characters or less")
    private String city;

    @Column(name = "state")
    @Size(min = 2, max = 2, message = "State must be 2 character abbreviation")
    private String state;

    @Column(name = "zip_code")
    @Pattern(regexp = "\\d{5}", message = "Zip Code must be exactly 5-digits long")
    private String zipCode;

    @Column(name = "phone")
    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits, no spaces or hyphens")
    private String phone;

    @Column(name = "fax")
    @Pattern(regexp = "\\d{10}", message = "Fax must be 10 digits, no spaces or hyphens")
    private String fax;

    @Column(name = "email")
    @Size(min = 5, max = 120, message = "Email must be valid and 120 characters or less")
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "notes")
    @Size(max = 3000)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    @Valid
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    @Valid
    private Employee manager;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamptz")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "modified_at", nullable = false, updatable = false, columnDefinition = "timestamptz")
    @UpdateTimestamp
    private Instant modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, updatable = false, referencedColumnName = "id")
    Employee createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by", nullable = false, updatable = false, referencedColumnName = "id")
    Employee modifiedBy;

    @Column(name = "deleted")
    private boolean deleted = false;

    @PrePersist
    private void onCreate(){
        this.createdAt = Instant.now();
        this.modifiedAt = Instant.now();
    }

    @PreUpdate
    private void onUpdate(){
        this.modifiedAt = Instant.now();
    }
}
