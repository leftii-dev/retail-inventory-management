package dev.austinbarnes.retailinventorymanagement.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
@EnableRedisHttpSession
public class SecurityConfig {
    @Bean
    SecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( auth -> {
                    auth.requestMatchers("/api/v1/shop/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2Client(withDefaults())
                .formLogin(withDefaults())
                .build();
    }
}
