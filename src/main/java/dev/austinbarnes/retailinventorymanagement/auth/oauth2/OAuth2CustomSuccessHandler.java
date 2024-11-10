package dev.austinbarnes.retailinventorymanagement.auth.oauth2;

import dev.austinbarnes.retailinventorymanagement.auth.CustomUserPrincipal;
import dev.austinbarnes.retailinventorymanagement.auth.credentiallogin.CustomUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2CustomSuccessHandler implements AuthenticationSuccessHandler {
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

        response.sendRedirect("/");
    }
}
