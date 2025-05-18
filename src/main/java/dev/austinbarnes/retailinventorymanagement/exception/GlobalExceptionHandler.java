package dev.austinbarnes.retailinventorymanagement.exception;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import jakarta.persistence.EntityNotFoundException;
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

/**
 * GlobalExceptionHandler handles all exceptions thrown in the application.
 * <p>
 * It provides methods to handle specific exceptions and return appropriate HTTP responses.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation exceptions thrown during request validation.
     *
     * @param ex the MethodArgumentNotValidException
     * @return a ResponseEntity with a bad request response and validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        List<ApiResponseDto.ValidationError> validationErrors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            validationErrors.add(ApiResponseDto.ValidationError.of(error.getField(), error.getDefaultMessage()));
        }

        return ApiResponseDto.badRequest("Validation failed", validationErrors);
    }

    /**
     * Handles unexpected exceptions thrown in the application.
     *
     * @param ex the Exception
     * @return a ResponseEntity with an internal server error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Void>> handleUnexpectedErrors(Exception ex) {
        log.error("Unexpected error occurred", ex);

        return ApiResponseDto.internalError("An unknown error occurred");
    }

    /**
     * Handles exceptions thrown when a Username is not found.
     *
     * @param ex the UsernameNotFoundException
     * @return a ResponseEntity with a bad request response
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleUsernameNotFoundException(Exception ex){
        log.error("Username Not Found", ex);

        return ApiResponseDto.badRequest("Username Not Found");
    }

    /**
     * Handles exceptions thrown when bad credentials are used.
     *
     * @param ex the BadCredentialsException
     * @return a ResponseEntity with an unauthorized response
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleBadCredentialsException(Exception ex){
        log.error("Bad credentials occurred", ex);

        return ApiResponseDto.unauthorized("Invalid credentials");
    }

    /**
     * Handles exceptions thrown when authorization is denied.
     *
     * @param ex the AuthorizationDeniedException
     * @return a ResponseEntity with an unauthorized response
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleAuthorizationDeniedException(Exception ex){
        log.error("Authorization Denied", ex);

        return ApiResponseDto.unauthorized("Unauthorized - Ensure you are logged in and try again");
    }

    /**
     * Handles exceptions thrown when a resource is not found.
     *
     * @param ex the NoResourceFoundException
     * @return a ResponseEntity with a not found response
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleNoResourceFoundException(Exception ex){
        log.error("No resource found", ex);

        return ApiResponseDto.notFound("No resource found");
    }

    /**
     * Handles exceptions thrown when an activation token is not found.
     *
     * @param ex the ActivationTokenNotFoundException
     * @return a ResponseEntity with a bad request response
     */
    @ExceptionHandler(ActivationTokenNotFoundException.class)
    public ResponseEntity<ApiResponseDto<String>> handleActivationTokenNotFoundException(Exception ex){
        log.error("Activation Token Not Found", ex);

        return ApiResponseDto.badRequest("Activation token not found. Token may have expired. Try logging in to issue a new token.");
    }

    /**
     * Handles exceptions thrown when an account is disabled.
     *
     * @param ex the DisabledException
     * @return a ResponseEntity with an unauthorized response
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiResponseDto<String>> handleDisabledException(Exception ex){
        log.error("Account Disabled", ex);

        return ApiResponseDto.unauthorized("Account Disabled. Check email for activation link or try again.");
    }

    /**
     * Handles exceptions thrown when an account is not active.
     *
     * @param ex the AccountNotActiveException
     * @return a ResponseEntity with a bad request response
     */
    @ExceptionHandler(AccountNotActiveException.class)
    public ResponseEntity<ApiResponseDto<String>> handleAccountNotActiveException(Exception ex){
        log.error("Account Not Active", ex);

        return ApiResponseDto.badRequest("Account Not Active. Check your email for the activation link and try again.");
    }

    /**
     * Handles exceptions thrown when an account activation token has expired.
     *
     * @param ex the AccountActivationTokenExpiredException
     * @return a ResponseEntity with a bad request response
     */
    @ExceptionHandler(AccountActivationTokenExpiredException.class)
    public ResponseEntity<ApiResponseDto<String>> handleAccountActivationTokenExpiredException(Exception ex){
        log.error("Account Activation Token Expired", ex);

        return ApiResponseDto.badRequest("Activation token expired. Check your email for a new activation link");
    }


    /**
     * Handles exceptions thrown when an entity is not found.
     *
     * @param ex the EntityNotFoundException
     * @return a ResponseEntity with a bad request response
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponseDto<String>> handleEntityNotFoundException(Exception ex) {
        log.error("Entity Not Found", ex);

        return ApiResponseDto.badRequest(ex.getMessage());
    }

    /**
     * Handles exceptions thrown when a duplicate email registration is attempted.
     *
     * @param ex the DuplicateEmailRegistrationException
     * @return a ResponseEntity with a bad request response
     */
    @ExceptionHandler(DuplicateEmailRegistrationException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleDuplicateEmailRegistrationException(DuplicateEmailRegistrationException ex){
        log.error("Duplicate Email Registration {} - {}", ex.getEmail(), ex.getMessage());

        return ApiResponseDto.badRequest("This email has already been registered. Try logging in.");

    }
}