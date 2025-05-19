package dev.austinbarnes.retailinventorymanagement.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

/**
 * SessionDebugFilter is a custom filter that logs session information and authorization requests.
 * It extends OncePerRequestFilter to ensure it is executed once per request.
 * This filter is useful for debugging purposes to inspect session attributes.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class SessionDebugFilter extends OncePerRequestFilter {

    public static final String DEFAULT_AUTHORIZATION_REQUEST_ATTR_NAME =
            "org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository.AUTHORIZATION_REQUEST";

    /**
     * This method is called for each request to log session information and authorization requests.
     * It retrieves the current session and logs its ID and attributes.
     *
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param filterChain the FilterChain object
     * @throws ServletException if an error occurs during filtering
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        log.debug("Request URI: {}", request.getRequestURI());
        log.debug("Session exists: {}", session != null);

        if (session != null) {
            log.debug("Session ID: {}", session.getId());

            // Log the authorization request state
            Map<String, OAuth2AuthorizationRequest> authorizationRequests = (Map<String, OAuth2AuthorizationRequest>)
                    session.getAttribute(DEFAULT_AUTHORIZATION_REQUEST_ATTR_NAME);

            log.debug("Authorization requests in session: {}",
                    authorizationRequests != null ? authorizationRequests.keySet() : "null");
        }

        filterChain.doFilter(request, response);
    }
}