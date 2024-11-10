package dev.austinbarnes.retailinventorymanagement.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public record ApiResponseDto<T>(
        T data,
        String message,
        boolean success,
        Instant timestamp,
        List<ValidationError> validationErrors
) {
    public record ValidationError(String field, String message) {
        public static ValidationError of(String field, String message){
            return new ValidationError(field, message);
        }
    }

    public static <T, R extends T> ResponseEntity<ApiResponseDto<T>> ok(R data) {
        return ResponseEntity.ok(new ApiResponseDto<T>(
                data, "Success", true, Instant.now(), new ArrayList<>()
        ));
    }

    public static <T> ResponseEntity<ApiResponseDto<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<T>(
                data, "Created successfully", true, Instant.now(), new ArrayList<>()
        ));
    }

    public static <T> ResponseEntity<ApiResponseDto<T>> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponseDto<T>(
                null, "No content", true, Instant.now(), new ArrayList<>()
        ));
    }

    public static <T> ResponseEntity<ApiResponseDto<T>> badRequest(String message, List<ValidationError> error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto<T>(
                null, message, false, Instant.now(), error
        ));
    }

    public static <T> ResponseEntity<ApiResponseDto<T>> badRequest(String message) {
        return badRequest(message, new ArrayList<>());
    }
    public static <T> ResponseEntity<ApiResponseDto<T>> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDto<T>(
                null, message, false, Instant.now(), new ArrayList<>()
        ));
    }

    public static <T> ResponseEntity<ApiResponseDto<T>> forbidden(String message) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponseDto<T>(
                null, message, false, Instant.now(), new ArrayList<>()
        ));
    }

    public static <T> ResponseEntity<ApiResponseDto<T>> unauthorized(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto<T>(
                null, message, false, Instant.now(), new ArrayList<>()
        ));
    }

    public static <T> ResponseEntity<ApiResponseDto<T>> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ApiResponseDto<T>(
                null, message, false, Instant.now(), new ArrayList<>()
        ));
    }

    public static <T> ResponseEntity<ApiResponseDto<T>> internalError(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto<T>(
               null, message, false, Instant.now(), new ArrayList<>()
        ));
    }
}
