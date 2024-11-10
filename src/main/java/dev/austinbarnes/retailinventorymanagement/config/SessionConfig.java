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

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class SessionConfig {

    @Value("${app.frontend.url}")
    private String frontendUrl; // pulls frontendUrl from application.properties if frontend is different domain

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("SESSION");
        serializer.setCookiePath("/");
        serializer.setSameSite("Lax");
        serializer.setUseSecureCookie(true);
        serializer.setCookieMaxAge(3600);

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

    private String extractDomain(String url) {
        try {
            return URI.create(url).getHost();

        } catch(Exception e) {
            return "";
        }
    }
}