package com.epam.esm.gift_certificate.exception;

public class TagCreationException extends Exception {

    public TagCreationException() {
    }

    public TagCreationException(String message) {
        super(message);
    }

    public TagCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TagCreationException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
