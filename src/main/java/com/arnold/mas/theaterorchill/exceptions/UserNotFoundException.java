// UserNotFoundException used by UserService class to address user queries for non-existent users.

package com.arnold.mas.theaterorchill.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
