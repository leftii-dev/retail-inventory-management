package dev.austinbarnes.retailinventorymanagement.exception;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
}