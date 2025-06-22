package dev.austinbarnes.retailinventorymanagement.auth.credentiallogin;

import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CustomUserDetails implements UserDetails interface to provide user
 * authentication and authorization information.
 * It contains user details such as username, password, and roles.
 */
public class CustomUserDetails implements UserDetails {

    private final User user;
    private final String loginIdentifier;

    /**
     * Constructor to create CustomUserDetails object.
     *
     * @param user the user entity
     * @param loginIdentifier the login identifier (username or email)
     */
    public CustomUserDetails(User user, String loginIdentifier) {
        this.user = user;
        this.loginIdentifier = loginIdentifier;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(user.getEmployee() != null) {
            return Stream.concat(
                            // Map User roles to authorities (e.g., ROLE_ADMIN, ROLE_EMPLOYEE)
                            user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())),
                            // Map Employee permissions to authorities (e.g., READ_PRODUCTS, WRITE_INVENTORY)
                            user.getEmployee().getEmployeePermissions().stream()
                                    .map(empPerm -> new SimpleGrantedAuthority(empPerm.getPermission().getName())))
                    .collect(Collectors.toSet());
        } else {
            return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).collect(Collectors.toSet());
        }
    }


    /**
     * Returns the user entity.
     *
     * @return the user entity
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the login identifier (username or email).
     *
     * @return the login identifier
     */
    @Override
    public String getUsername() {
        return loginIdentifier;
    }

    /**
     * Returns the expiration status of user entity.
     *
     * @return the expiration status of user entity
     */
    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    /**
     * Returns the locked status of user entity.
     *
     * @return the locked status of user entity
     */
    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    /**
     * Returns the credentials expiration status of user entity.
     *
     * @return the credentials expiration status of user entity
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    /**
     * Returns the enabled status of user entity.
     *
     * @return the enabled status of user entity
     */
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
