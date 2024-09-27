package dev.austinbarnes.retailinventorymanagement.location;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Lazy;

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
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Employee manager;

    @Column(name = "deleted")
    private boolean deleted = false;
}
