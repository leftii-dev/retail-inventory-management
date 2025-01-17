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
 * Represents a user in the system with associated authentication and profile details.
 * The User class extends the {@link BaseEntity} and includes fields such as email,
 * name, password, OAuth provider information, associated employee, roles, and account status.
 *
 * Fields:
 * - email: The unique email address of the user. Validated for proper email format.
 * - name: The name of the user. Must be between 2 and 50 characters.
 * - password: The password associated with the user's account.
 * - oauthProvider: Specifies the OAuth provider if the user is authenticated via third-party providers.
 * - oauthProviderId: Unique identifier for the user in the OAuth provider's system.
 * - employee: The linked {@link Employee} entity representing employment details.
 * - pictureUrl: The URL of the user's profile picture.
 * - roles: The set of {@link Role} entities assigned to the user, determining permissions.
 * - enabled: Flag to indicate if the user's account is active.
 * - accountNonExpired: Flag to indicate if the account has not expired.
 * - accountNonLocked: Flag to indicate if the account is not locked.
 * - credentialsNonExpired: Flag to indicate if the credentials are still valid.
 *
 * This entity is annotated as a JPA entity and mapped to the database table "app_user".
 * Relationships include a one-to-one mapping with the Employee entity
 * and a many-to-many relationship with the Role entity via the "user_roles" join table.
 */
@Entity
@Table(name = "app_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"password", "oauthProvider", "oauthProviderId", "employee", "roles"}, callSuper = true)
public class User extends BaseEntity {

    @Column(name = "email", unique = true)
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "name")
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
