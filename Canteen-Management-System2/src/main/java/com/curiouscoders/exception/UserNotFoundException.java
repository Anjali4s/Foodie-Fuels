package com.curiouscoders.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "User not found";

    // Default constructor with a generic message
    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    // Constructor for a specific message
    public UserNotFoundException(String message) {
        super(message);
    }

    // Constructor for "User not found with ID"
    public UserNotFoundException(Long id) {
        super("User not found with ID: " + id);
    }

    // Constructor for "User not found with field value"
    public UserNotFoundException(String field, String value) {
        super("User not found with " + field + ": " + value);
    }
}

