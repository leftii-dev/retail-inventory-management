package dev.austinbarnes.retailinventorymanagement.auth.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * OAuth2CustomFailureHandler is a custom implementation of AuthenticationFailureHandler.
 * It handles authentication failures during OAuth2 login process.
 * It logs the error details and redirects the user to a custom error page.
 */
@Component
@Slf4j
public class OAuth2CustomFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // Log the exception with detailed information
        log.error("OAuth2 authentication failure:", exception);

        String errorCode = (String) request.getSession().getAttribute("error");
        String errorDescription = (String) request.getSession().getAttribute("error_description");

        log.error("OAuth2 error code: {}", errorCode);
        log.error("OAuth2 error description: {}", errorDescription);

        request.getSession().removeAttribute("error");
        request.getSession().removeAttribute("error_description");

        response.sendRedirect("http://localhost:3000/auth/callback");
    }

}