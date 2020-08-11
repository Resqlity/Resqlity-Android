package com.resqlity.orm.exceptions;

public class ResqlityDbException extends Exception {

    public ResqlityDbException(String message) {
        super(message);
    }

    public ResqlityDbException(String message, Throwable cause) {
        super(message, cause);
    }

}
