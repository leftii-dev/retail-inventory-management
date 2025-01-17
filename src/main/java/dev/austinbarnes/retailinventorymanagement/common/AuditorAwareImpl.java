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

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditorAwareImpl implements AuditorAware<UUID> {

    @Value("${system.employee.id}")
    private UUID systemEmployeeId;

    @Override
    public Optional<UUID> getCurrentAuditor() {
        UUID employeeId = getAuthenticatedUser();
        return employeeId != null
                ?
                Optional.of(employeeId)
                :
                Optional.of(systemEmployeeId);
    }


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
