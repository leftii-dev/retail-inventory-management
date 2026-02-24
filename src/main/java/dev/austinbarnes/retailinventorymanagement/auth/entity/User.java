package dev.austinbarnes.retailinventorymanagement.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

/**
 * User represents a user in the system.
 * It contains the user's email, name, password, and roles.
 * The email must be unique and validated for proper format.
 * The name has a size constraint between 2 and 50 characters.
 * The password is stored securely and is not exposed in toString methods.
 */
@Entity
@Table(name = "app_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"password", "oauthProvider", "oauthProviderId", "employee", "roles"}, callSuper = true)
public class User extends BaseEntity {

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "name", nullable = false)
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "oauth_provider")
    private String oauthProvider;

    @Column(name = "oauth_provider_id", unique = true)
    private String oauthProviderId;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Employee employee;

    @Column(name = "picture_url")
    private String pictureUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    private boolean enabled = false;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
}
