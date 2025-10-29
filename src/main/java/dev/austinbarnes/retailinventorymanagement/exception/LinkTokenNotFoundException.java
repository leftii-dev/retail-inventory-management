package dev.austinbarnes.retailinventorymanagement.exception;

public class LinkTokenNotFoundException extends RuntimeException{
    public LinkTokenNotFoundException(String token) {
        super("Link token %s expired or does not exist.".formatted(token));
    }
}
