package dev.austinbarnes.retailinventorymanagement.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * ApiResponseDto is a generic class that represents a standard API response structure.
 * It contains fields for data, message, success status, timestamp, and validation errors.
 * It should be used as the standard response format for all API endpoints.
 *
 * @param <T> the type of the data field
 */
public record ApiResponseDto<T>(
        T data,
        String message,
        boolean success,
        Instant timestamp,
        List<ValidationError> validationErrors
) {
    /**
     * ValidationError is a record that represents a validation error.
     * It contains fields for the field name and the error message.
     *
     * @param field   the name of the field that caused the validation error
     * @param message the error message
     */
    public record ValidationError(String field, String message) {
        /**
         * Factory method to create a new ValidationError instance.
         *
         * @param field   the name of the field that caused the validation error
         * @param message the error message
         * @return a new ValidationError instance
         */
        public static ValidationError of(String field, String message){
            return new ValidationError(field, message);
        }
    }

    /**
     * Factory method to create a new ApiResponseDto instance with Success status.
     *
     * @param data    the data to include in the response
     * @param <T>     the type of the data field
     * @return a new ApiResponseDto instance
     */
    public static <T, R extends T> ResponseEntity<ApiResponseDto<T>> ok(R data) {
        return ResponseEntity.ok(new ApiResponseDto<T>(
                data, "Success", true, Instant.now(), new ArrayList<>()
        ));
    }

    /**
     * Factory method to create a new ApiResponseDto instance with Created status.
     *
     * @param data    the data to include in the response
     * @param <T>     the type of the data field
     * @return a new ApiResponseDto instance
     */
    public static <T> ResponseEntity<ApiResponseDto<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<T>(
                data, "Created successfully", true, Instant.now(), new ArrayList<>()
        ));
    }

    /**
     * Factory method to create a new ApiResponseDto instance with No Content status.
     *
     * @param <T> the type of the data field
     * @return a new ApiResponseDto instance
     */
    public static <T> ResponseEntity<ApiResponseDto<T>> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponseDto<T>(
                null, "No content", true, Instant.now(), new ArrayList<>()
        ));
    }

    /**
     * Factory method to create a new ApiResponseDto instance with Bad Request status.
     *
     * @param message the error message
     * @param error   the list of validation errors
     * @param <T>     the type of the data field
     * @return a new ApiResponseDto instance
     */
    public static <T> ResponseEntity<ApiResponseDto<T>> badRequest(String message, List<ValidationError> error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto<T>(
                null, message, false, Instant.now(), error
        ));
    }

    /**
     * Factory method to create a new ApiResponseDto instance with Bad Request status.
     *
     * @param message the error message
     * @param <T>     the type of the data field
     * @return a new ApiResponseDto instance
     */
    public static <T> ResponseEntity<ApiResponseDto<T>> badRequest(String message) {
        return badRequest(message, new ArrayList<>());
    }

    /**
     * Factory method to create a new ApiResponseDto instance with Not Found status.
     *
     * @param message the error message
     * @param <T>     the type of the data field
     * @return a new ApiResponseDto instance
     */
    public static <T> ResponseEntity<ApiResponseDto<T>> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDto<T>(
                null, message, false, Instant.now(), new ArrayList<>()
        ));
    }

    /**
     * Factory method to create a new ApiResponseDto instance with Forbidden status.
     *
     * @param message the error message
     * @param <T>     the type of the data field
     * @return a new ApiResponseDto instance
     */
    public static <T> ResponseEntity<ApiResponseDto<T>> forbidden(String message) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponseDto<T>(
                null, message, false, Instant.now(), new ArrayList<>()
        ));
    }

    /**
     * Factory method to create a new ApiResponseDto instance with Unauthorized status.
     *
     * @param message the error message
     * @param <T>     the type of the data field
     * @return a new ApiResponseDto instance
     */
    public static <T> ResponseEntity<ApiResponseDto<T>> unauthorized(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto<T>(
                null, message, false, Instant.now(), new ArrayList<>()
        ));
    }

    /**
     * Factory method to create a new ApiResponseDto instance with the ability to specify the status.
     *
     * @param message the error message
     * @param <T>     the type of the data field
     * @return a new ApiResponseDto instance
     */
    public static <T> ResponseEntity<ApiResponseDto<T>> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ApiResponseDto<T>(
                null, message, false, Instant.now(), new ArrayList<>()
        ));
    }

    /**
     * Factory method to create a new ApiResponseDto instance with Internal Server Error status.
     *
     * @param message the error message
     * @param <T>     the type of the data field
     * @return a new ApiResponseDto instance
     */
    public static <T> ResponseEntity<ApiResponseDto<T>> internalError(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto<T>(
               null, message, false, Instant.now(), new ArrayList<>()
        ));
    }
}
