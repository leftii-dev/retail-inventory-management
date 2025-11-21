package dev.austinbarnes.retailinventorymanagement.common;

import dev.austinbarnes.retailinventorymanagement.auth.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * AuditorAwareImpl provides the currently logged-in user ID or system user ID.
 * Enables automatic population of auditing fields in entities.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditorAwareImpl implements AuditorAware<UUID> {

    @Value("${system.employee.id}")
    private UUID systemEmployeeId;

    /**
     * Returns the ID of the current auditor.
     * If no user is authenticated, it returns the system employee ID.
     *
     * @return an Optional containing the current auditor's ID
     */
    @Override
    public Optional<UUID> getCurrentAuditor() {
        UUID employeeId = getAuthenticatedUser();
        return employeeId != null
                ?
                Optional.of(employeeId)
                :
                Optional.of(systemEmployeeId);
    }

    /**
     * Retrieves the ID of the currently authenticated user.
     * If no user is authenticated, it returns null.
     *
     * @return the ID of the currently authenticated user, or null if no user is authenticated
     */
    private UUID getAuthenticatedUser() {
        var context = SecurityContextHolder.getContext();
        if(context == null || context.getAuthentication() == null) return null;
        Object principal = context.getAuthentication().getPrincipal();
        if("anonymousUser".equals(principal)) return null;
        if(principal instanceof CustomUserPrincipal user) {
            try{
                return user.getUser().getEmployee().getId();
            } catch(Exception e){
                return null;
            }
        }
        return null;
    }
}
