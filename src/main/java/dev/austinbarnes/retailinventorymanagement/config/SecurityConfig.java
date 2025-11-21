package dev.austinbarnes.retailinventorymanagement.config;

import dev.austinbarnes.retailinventorymanagement.auth.credentiallogin.CustomAuthenticationProvider;
import dev.austinbarnes.retailinventorymanagement.auth.oauth2.CustomAuthorizationRequestResolver;
import dev.austinbarnes.retailinventorymanagement.auth.oauth2.OAuth2CustomFailureHandler;
import dev.austinbarnes.retailinventorymanagement.auth.oauth2.OAuth2CustomSuccessHandler;
import dev.austinbarnes.retailinventorymanagement.auth.oauth2.OAuth2UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * SecurityConfig is a Spring configuration class that sets up the security filter chain for the application.
 * It configures authentication, authorization, CORS, and session management.
 * It also defines beans for custom OAuth2 user service and custom success/failure handlers.
 */
@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Slf4j
public class SecurityConfig {

    private final OAuth2UserService oAuthUserService;
    private final OAuth2CustomSuccessHandler oAuth2CustomSuccessHandler;
    private final OAuth2CustomFailureHandler oAuth2CustomFailureHandler;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    /**
     * Configures the security filter chain for the application.
     * It sets up authentication, authorization, CORS, and session management.
     *
     * @param http the HttpSecurity object to configure
     * @param clientRegistrationRepository the ClientRegistrationRepository for OAuth2
     * @return a configured SecurityFilterChain instance
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, InMemoryClientRegistrationRepository clientRegistrationRepository) throws Exception {
        log.info("Google Client ID: '" + clientRegistrationRepository.findByRegistrationId("google").getClientId() + "'");
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/**",
                                            "/login/oauth2/code/**",
                                            "/login/**",
                                            "/oauth2/**",
                                            "/favicon.ico",
                                            "/swagger-ui.html",
                                            "/swagger-ui/**",
                                            "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(customAuthenticationProvider)
                .exceptionHandling(exc -> exc
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .oauth2Login(oauth2 -> oauth2
//                        .authorizationEndpoint(authorizationEndpointConfig ->
//                                authorizationEndpointConfig.authorizationRequestResolver(customAuthorizationRequestResolver(clientRegistrationRepository)))
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuthUserService)
                        )
                        .successHandler(oAuth2CustomSuccessHandler)
                        .failureHandler(oAuth2CustomFailureHandler)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .expiredUrl("/login?expired")
                )
                .build();

    }

    /**
     * Configures CORS settings for the application.
     * It allows requests from specific origins and sets allowed methods and headers.
     *
     * @return a configured CorsConfigurationSource instance
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // TODO: Update for production
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // should become https://inventory-system.austinbarnes.dev
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Configures the AuthenticationManager for the application.
     * It uses the shared AuthenticationManagerBuilder from the HttpSecurity object.
     *
     * @param http the HttpSecurity object to configure
     * @return a configured AuthenticationManager instance
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        return auth.build();
    }

    /**
     * Creates a new CustomAuthorizationRequestResolver instance.
     * This resolver is used to customize the authorization request for OAuth2 login.
     *
     * @param clientRegistrationRepository the ClientRegistrationRepository for OAuth2
     * @return a new CustomAuthorizationRequestResolver instance
     */
    @Bean
    public CustomAuthorizationRequestResolver customAuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository){
        return new CustomAuthorizationRequestResolver(clientRegistrationRepository);
    }
}
