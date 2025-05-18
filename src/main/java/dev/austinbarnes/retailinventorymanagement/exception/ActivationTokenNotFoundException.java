package dev.austinbarnes.retailinventorymanagement.exception;

/**
 * AccountActivationTokenExpiredException is a custom exception that is thrown when an account activation token has expired.
 * This exception extends RuntimeException and provides a default message indicating the reason for the exception.
 */
public class ActivationTokenNotFoundException extends RuntimeException {
    public ActivationTokenNotFoundException(String token) {
        super(token);
    }
}
