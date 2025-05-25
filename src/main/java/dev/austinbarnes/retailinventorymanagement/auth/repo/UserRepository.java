package dev.austinbarnes.retailinventorymanagement.auth.repo;

import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
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

    @Modifying
    @Query("UPDATE User u SET u.active = false WHERE u.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(User user) {
        softDeleteById(user.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
