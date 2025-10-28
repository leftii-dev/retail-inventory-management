package dev.austinbarnes.retailinventorymanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import java.net.URI;

/**
 * SessionConfig is a Spring configuration class that sets up Redis-based HTTP sessions.
 * It configures the session timeout, cookie settings, and Redis connection factory.
 * The session timeout is set to 1800 seconds (30 Minutes).
 */
@Configuration
@EnableRedisHttpSession()
public class SessionConfig {

    @Value("${app.frontend.url}")
    private String frontendUrl; // pulls frontendUrl from application.properties if frontend is different domain

    @Value("${spring.data.redis.host:localhost}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    /**
     * Configures the cookie serializer for session management.
     * It sets the cookie name, path, SameSite attribute, secure flag, and max age.
     * If the frontend URL is different from the backend, it sets the domain name for the cookie.
     *
     * @return a configured CookieSerializer instance
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("SESSION");
        serializer.setCookiePath("/");
        serializer.setSameSite("Lax");
        serializer.setUseSecureCookie(false); // Change for prod
        serializer.setCookieMaxAge(1800);

        // If frontend and backend are on different domains
        if(!frontendUrl.isEmpty()) {
            String domain = extractDomain(frontendUrl);
            serializer.setDomainName(domain);
        } else {
            serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$"); // Allows sharing between subdomains

        }

        serializer.setRememberMeRequestAttribute("remember-me");
        serializer.setUseHttpOnlyCookie(true);
        return serializer;
    }

    /**
     * Extracts the domain name from the given URL.
     * If the URL is invalid, it returns an empty string.
     *
     * @param url the URL to extract the domain from
     * @return the extracted domain name, or an empty string if the URL is invalid
     */
    private String extractDomain(String url) {
        try {
            return URI.create(url).getHost();

        } catch(Exception e) {
            return "";
        }
    }
}