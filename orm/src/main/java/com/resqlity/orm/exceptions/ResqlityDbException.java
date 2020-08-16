package com.resqlity.orm.exceptions;

/**
 * Global ResqlityDbException On CRUD Operations
 */
public class ResqlityDbException extends Exception {

    public ResqlityDbException(String message) {
        super(message);
    }

    public ResqlityDbException(String message, Throwable cause) {
        super(message, cause);
    }

}
