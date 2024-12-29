package dev.austinbarnes.retailinventorymanagement.exception;

import lombok.Getter;

@Getter
public class DuplicateEmailRegistrationException extends RuntimeException {
    private final String email;

    public DuplicateEmailRegistrationException(String email, String message) {
        super(message);
        this.email = email;
    }
}
