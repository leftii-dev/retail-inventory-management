package dev.austinbarnes.retailinventorymanagement.exception;

public class AccountActivationTokenExpiredException extends RuntimeException {
    public AccountActivationTokenExpiredException() {
        super("Account activation token has expired");
    }
}
