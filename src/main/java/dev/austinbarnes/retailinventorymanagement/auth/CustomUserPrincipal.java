package dev.austinbarnes.retailinventorymanagement.auth;

import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * CustomUserPrincipal implements OAuth2User and UserDetails interfaces.
 * It represents the authenticated user in the application.
 * It contains user details such as username, password, roles, and attributes.
 */
@Data
public class CustomUserPrincipal implements OAuth2User, UserDetails {
    private final User user;
    private UUID id;
    private String email;
    private String name;
    private String password;
    private String loginIdentifier;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    /**
     * Constructor to create CustomUserPrincipal object.
     *
     * @param user            the user entity
     * @param loginIdentifier the login identifier (username or email)
     */
    public CustomUserPrincipal(User user, String loginIdentifier) {
        this.user = user;
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
        this.loginIdentifier = loginIdentifier;
        this.authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).collect(Collectors.toList());
        this.enabled = user.isEnabled();
        this.accountNonExpired = user.isAccountNonExpired();
        this.accountNonLocked = user.isAccountNonLocked();
        this.credentialsNonExpired = user.isCredentialsNonExpired();
    }

    /**
     * Factory method for OAuth2 authentication.
     *
     * @param user            the user entity
     * @param attributes      the attributes from OAuth2 provider
     * @param loginIdentifier the login identifier (username or email)
     * @return CustomUserPrincipal object
     */
    public static CustomUserPrincipal create(User user, Map<String, Object> attributes, String loginIdentifier) {
        CustomUserPrincipal userPrincipal = new CustomUserPrincipal(user, loginIdentifier);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    /**
     * Factory method for credential login.
     *
     * @param user            the user entity
     * @param loginIdentifier the login identifier (username or email)
     * @return CustomUserPrincipal object
     */
    public static CustomUserPrincipal create(User user, String loginIdentifier) {
        return new CustomUserPrincipal(user, loginIdentifier);
    }

    @Override
    public String getUsername() {
        return this.loginIdentifier;
    }

    @Override
    public String getPassword() {
        return attributes == null ? password : null;
    }

    @Override
    public String getName() {
        return name != null ? name : email;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes == null ? new HashMap<>() : attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
