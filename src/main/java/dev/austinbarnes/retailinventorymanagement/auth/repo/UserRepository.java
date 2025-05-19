package dev.austinbarnes.retailinventorymanagement.auth.repo;

import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * UserRepository is a Spring Data JPA repository for managing User entities.
 * It provides methods to find a user by email and by OAuth provider and ID.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByOauthProviderAndOauthProviderId(String oauthProvider, String oauthProviderId);
}
