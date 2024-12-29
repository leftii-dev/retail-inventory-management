package dev.austinbarnes.retailinventorymanagement.exception;

public class ActivationTokenNotFoundException extends RuntimeException {
    public ActivationTokenNotFoundException(String token) {
        super(token);
    }
}
