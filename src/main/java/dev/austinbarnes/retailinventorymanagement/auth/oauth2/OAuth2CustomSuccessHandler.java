package dev.austinbarnes.retailinventorymanagement.auth.oauth2;

import dev.austinbarnes.retailinventorymanagement.auth.CustomUserPrincipal;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * OAuth2CustomSuccessHandler is a custom implementation of AuthenticationSuccessHandler.
 * It handles successful authentication for OAuth2 login process.
 * It retrieves user information from the OAuth2 provider and stores it in the session.
 */
@Slf4j
@Component
public class OAuth2CustomSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * Handles successful authentication for OAuth2 login.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param authentication the authentication object containing user details
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String provider = String.valueOf(token.getAuthorizedClientRegistrationId());



        Object principalObj = authentication.getPrincipal();
        if(principalObj instanceof CustomUserPrincipal principal) {
            HttpSession session = request.getSession();
            UUID userId = principal.getId();

            OAuth2UserInfo userInfo;

            switch( provider ){
                case "google" -> userInfo = new GoogleOAuth2UserInfo(principal.getAttributes());
                case "github" -> userInfo = new GithubOAuth2UserInfo(principal.getAttributes());
                default -> throw new ServletException("Unsupported provider: " + provider);
            }

            session.setAttribute("userId", userId.toString());
            session.setAttribute("providerUserId", userInfo.getId());
            session.setAttribute("email", userInfo.getEmail());
            session.setAttribute("name", userInfo.getName());
            session.setAttribute("isAuthenticated", true);

            session.setMaxInactiveInterval(1800);
            log.info("User {} logged in with ID {} (session {})",
                    principal.getEmail(), principal.getId(), session.getId());
        } else {
            log.warn("Principal is not CustomUserPrincipal {}", authentication.getPrincipal().getClass().getName());
        }


        response.sendRedirect("http://localhost:3000/auth/callback");
    }
}
