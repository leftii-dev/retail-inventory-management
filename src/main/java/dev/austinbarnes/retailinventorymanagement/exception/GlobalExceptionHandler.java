package dev.austinbarnes.retailinventorymanagement.exception;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle @Valid validation failures
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        List<ApiResponseDto.ValidationError> validationErrors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            validationErrors.add(ApiResponseDto.ValidationError.of(error.getField(), error.getDefaultMessage()));
        }

        return ApiResponseDto.badRequest("Validation failed", validationErrors);
    }

    // Handle unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Void>> handleUnexpectedErrors(Exception ex) {
        log.error("Unexpected error occurred", ex);

        return ApiResponseDto.internalError("An unknown error occurred");
    }

    // Handle Username not found exception
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleUsernameNotFoundException(Exception ex){
        log.error("Username Not Found", ex);

        return ApiResponseDto.badRequest("Username Not Found");
    }

    // Handle Bad Credentials Exceptions
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleBadCredentialsException(Exception ex){
        log.error("Bad credentials occurred", ex);

        return ApiResponseDto.unauthorized("Invalid credentials");
    }

    // Handle Authorization denied exception
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleAuthorizationDeniedException(Exception ex){
        log.error("Authorization Denied", ex);

        return ApiResponseDto.unauthorized("Unauthorized - Ensure you are logged in and try again");
    }

    // Handle NoResourceFound exception (bad endpoint/endpoint does not exist)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleNoResourceFoundException(Exception ex){
        log.error("No resource found", ex);

        return ApiResponseDto.notFound("No resource found");
    }

    // Handle ActivationTokenNotFound exception (bad token/expired)
    @ExceptionHandler(ActivationTokenNotFoundException.class)
    public ResponseEntity<ApiResponseDto<String>> handleActivationTokenNotFoundException(Exception ex){
        log.error("Activation Token Not Found", ex);

        return ApiResponseDto.badRequest("Activation token not found. Token may have expired. Try logging in to issue a new token.");
    }

    // Handle DisabledException when non-activated account attempts login
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleDisabledException(Exception ex){
        log.error("Account Disabled", ex);

        return ApiResponseDto.badRequest("Account Disabled. Check email for activation link or try again.");
    }

    // Handle AccountNotActiveException (Happens when activation token is not expired)
    @ExceptionHandler(AccountNotActiveException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleAccountNotActiveException(Exception ex){
        log.error("Account Not Active", ex);

        return ApiResponseDto.badRequest("Account Not Active. Check your email for the activation link and try again.");
    }

    // Handle AccountActivationTokenExpiredException (Happens when account was not activated and token expired)
    @ExceptionHandler(AccountActivationTokenExpiredException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleAccountActivationTokenExpiredException(Exception ex){
        log.error("Account Activation Token Expired", ex);

        return ApiResponseDto.badRequest("Activation token expired. Check your email for a new activation link");
    }

    // Handle DuplicateEmailRegistrationException (when user tries to create account with email that already exists)
    @ExceptionHandler(DuplicateEmailRegistrationException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleDuplicateEmailRegistrationException(DuplicateEmailRegistrationException ex){
        log.error("Duplicate Email Registration {} - {}", ex.getEmail(), ex.getMessage());

        return ApiResponseDto.badRequest("This email has already been registered. Try logging in.");
    }
}