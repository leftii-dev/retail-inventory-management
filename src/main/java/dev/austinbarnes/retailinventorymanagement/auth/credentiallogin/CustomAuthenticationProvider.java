package dev.austinbarnes.retailinventorymanagement.auth.credentiallogin;

import dev.austinbarnes.retailinventorymanagement.auth.CustomUserPrincipal;
import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.auth.repo.ActivationTokenRepository;
import dev.austinbarnes.retailinventorymanagement.auth.service.ActivationTokenService;
import dev.austinbarnes.retailinventorymanagement.exception.AccountActivationTokenExpiredException;
import dev.austinbarnes.retailinventorymanagement.exception.AccountNotActiveException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    private final ActivationTokenRepository activationTokenRepository;
    private final ActivationTokenService activationTokenService;

    public CustomAuthenticationProvider(ActivationTokenRepository activationTokenRepository,
                                        CustomUserDetailsService customUserDetailsService,
                                        PasswordEncoder encoder, ActivationTokenService activationTokenService) {
        super();
        this.activationTokenRepository = activationTokenRepository;
        this.activationTokenService = activationTokenService;
        setUserDetailsService(customUserDetailsService);
        setPasswordEncoder(encoder);
        setPreAuthenticationChecks(new CustomPreAuthenticationChecks());
    }

    private class CustomPreAuthenticationChecks implements UserDetailsChecker {
        @Override
        public void check(UserDetails userDetails) {
            User user = ((CustomUserPrincipal) userDetails).getUser();
            System.out.println("Enabled: " + userDetails.isEnabled());
            if(!userDetails.isEnabled()) {
                if(activationTokenRepository.findByUserId(user.getId()).isPresent()) {
                    throw new AccountNotActiveException("User account not activated. Check your email (possibly spam folder) to activate");
                } else {
                    // new token
                    activationTokenService.activateAndSendEmail(user.getId(), user.getEmail());
                    throw new AccountActivationTokenExpiredException();
                }
            }

            if (!userDetails.isAccountNonLocked()) {
                throw new LockedException("User account is locked");
            }

            if (!userDetails.isAccountNonExpired()) {
                throw new AccountExpiredException("User account has expired");
            }

            if (!userDetails.isCredentialsNonExpired()) {
                throw new CredentialsExpiredException("User credentials have expired");
            }
        }
    }
}
