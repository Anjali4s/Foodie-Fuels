package com.curiouscoders.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.curiouscoders.model.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle specific exceptions
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        // Creating a custom error response with error code and message
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),  // Current timestamp
            ex.getMessage(),      // The exception message
            "USER_NOT_FOUND"      // Custom status
        );
        // Returning error response with HTTP status 404 (Not Found)
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        // Creating a custom error response for general errors
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),  // Current timestamp
            "An error occurred.", // Generic message
            "INTERNAL_SERVER_ERROR" // Custom status
        );
        // Returning error response with HTTP status 500 (Internal Server Error)
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler({MenuItemNotFoundException.class}) // Pass exception inside curly braces
    public ResponseEntity<ErrorResponse> handleMenuItemNotFound(MenuItemNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            ex.getMessage(),
            "MENU_ITEM_NOT_FOUND"
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({EmployeeNotFoundException.class}) // Pass exception inside curly braces
    public ResponseEntity<ErrorResponse> handleEmployeeNotFound(EmployeeNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            ex.getMessage(),
            "MENU_ITEM_NOT_FOUND"
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}


