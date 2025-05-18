package dev.austinbarnes.retailinventorymanagement.exception;

import lombok.Getter;

/**
 * DuplicateEmailRegistrationException is a custom exception that is thrown when a user tries to register with an email
 * that already exists in the system.
 * <p>
 * This exception extends RuntimeException and provides a default message indicating the reason for the exception.
 */
@Getter
public class DuplicateEmailRegistrationException extends RuntimeException {
    private final String email;

    public DuplicateEmailRegistrationException(String email, String message) {
        super(message);
        this.email = email;
    }
}
