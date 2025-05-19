package dev.austinbarnes.retailinventorymanagement.auth.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * CustomAuthorizationRequestResolver is a custom implementation of OAuth2AuthorizationRequestResolver.
 * It modifies the authorization request to include additional parameters.
 * In this case, it adds a "prompt" parameter with the value "consent".
 */
public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {
    private final OAuth2AuthorizationRequestResolver defaultResolver;

    /**
     * Constructor to create CustomAuthorizationRequestResolver object.
     *
     * @param clientRegistrationRepository the client registration repository
     */
    public CustomAuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository) {
        this.defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(
                clientRegistrationRepository, OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI
        );
    }

    /**
     * Constructor to create CustomAuthorizationRequestResolver object with a custom authorization request base URI.
     *
     * @param clientRegistrationRepository the client registration repository
     * @param authorizationRequestBaseUri the base URI for authorization requests
     */
    public CustomAuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository,
                                              String authorizationRequestBaseUri) {
        this.defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(
                clientRegistrationRepository, authorizationRequestBaseUri
        );
    }

    /**
     * Resolves the OAuth2 authorization request from the given HTTP request.
     *
     * @param request the HTTP request
     * @return the OAuth2 authorization request
     */
    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        OAuth2AuthorizationRequest authRequest = this.defaultResolver.resolve(request);
        return customizeAuthorizationRequest(authRequest);
    }

    /**
     * Resolves the OAuth2 authorization request from the given HTTP request and client registration ID.
     *
     * @param request the HTTP request
     * @param clientRegistrationId the client registration ID
     * @return the OAuth2 authorization request
     */
    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        OAuth2AuthorizationRequest authRequest = this.defaultResolver.resolve(request, clientRegistrationId);
        return customizeAuthorizationRequest(authRequest);
    }

    /**
     * Customizes the OAuth2 authorization request by adding additional parameters.
     *
     * @param authRequest the original OAuth2 authorization request
     * @return the customized OAuth2 authorization request
     */
    private OAuth2AuthorizationRequest customizeAuthorizationRequest(
            OAuth2AuthorizationRequest authRequest) {
        if (authRequest == null) return null;

        Map<String, Object> additionalParameters =
                new HashMap<>(authRequest.getAdditionalParameters());
        additionalParameters.put("prompt", "consent");

        return OAuth2AuthorizationRequest
                .from(authRequest)
                .additionalParameters(additionalParameters)
                .build();
    }
}
