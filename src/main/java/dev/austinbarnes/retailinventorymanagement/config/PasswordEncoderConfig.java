package dev.austinbarnes.retailinventorymanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * PasswordEncoderConfig is a Spring configuration class that defines a PasswordEncoder bean.
 * The PasswordEncoder bean is used to encode passwords in the application.
 * In this case, it uses the BCryptPasswordEncoder implementation.
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * Creates a PasswordEncoder bean using the BCryptPasswordEncoder implementation.
     * The PasswordEncoder bean is used to encode passwords in the application.
     *
     * @return a configured PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}