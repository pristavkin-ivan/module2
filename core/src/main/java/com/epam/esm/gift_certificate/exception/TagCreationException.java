package com.epam.esm.gift_certificate.exception;

public class TagCreationException extends Exception {

    private String name;

    public TagCreationException() {
    }

    public TagCreationException(String message, String name) {
        super(message);
        this.name = name;
    }

    public TagCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TagCreationException(Throwable cause) {
        super(cause);
    }

    public String getName() {
        return name;
    }
}
