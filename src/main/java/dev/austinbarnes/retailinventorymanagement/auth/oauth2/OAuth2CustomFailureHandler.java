package dev.austinbarnes.retailinventorymanagement.auth.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class OAuth2CustomFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // Log the exception with detailed information
        log.error("OAuth2 authentication failure:", exception);

        // Retrieve more specific error details
        String errorCode = (String) request.getSession().getAttribute("error");
        String errorDescription = (String) request.getSession().getAttribute("error_description");

        // Log the additional error details
        log.error("OAuth2 error code: {}", errorCode);
        log.error("OAuth2 error description: {}", errorDescription);

        // Clear the session attributes to avoid leaking sensitive information
        request.getSession().removeAttribute("error");
        request.getSession().removeAttribute("error_description");

        // Optionally, you can also set a custom error message in the response
        response.sendRedirect("/login?error=true");
    }

}