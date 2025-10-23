package dev.austinbarnes.retailinventorymanagement.auth.oauth2;

import dev.austinbarnes.retailinventorymanagement.auth.CustomUserPrincipal;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * OAuth2CustomSuccessHandler is a custom implementation of AuthenticationSuccessHandler.
 * It handles successful authentication for OAuth2 login process.
 * It retrieves user information from the OAuth2 provider and stores it in the session.
 */
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

        HttpSession session = request.getSession();

        OAuth2UserInfo userInfo;

        switch( provider ){
            case "google" -> userInfo = new GoogleOAuth2UserInfo(((CustomUserPrincipal)authentication.getPrincipal()).getAttributes());
            case "github" -> userInfo = new GithubOAuth2UserInfo(((CustomUserPrincipal)authentication.getPrincipal()).getAttributes());
            default -> throw new ServletException("Unsupported provider: " + provider);
        }

        session.setAttribute("providerUserId", userInfo.getId());
        session.setAttribute("email", userInfo.getEmail());
        session.setAttribute("name", userInfo.getName());
        session.setAttribute("isAuthenticated", true);

        session.setMaxInactiveInterval(1800);

        response.sendRedirect("http://localhost:3000/auth/callback");
    }
}
