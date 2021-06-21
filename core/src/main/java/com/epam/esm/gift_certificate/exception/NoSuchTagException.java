package com.epam.esm.gift_certificate.exception;

public class NoSuchTagException extends Exception {
    public NoSuchTagException() {
        super();
    }

    public NoSuchTagException(String message) {
        super(message);
    }

    public NoSuchTagException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchTagException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
