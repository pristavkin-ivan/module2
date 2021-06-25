package com.epam.esm.gift_certificate.exception;

public class TagCreationException extends RuntimeException {

    private String name;

    public TagCreationException() {
    }

    public TagCreationException(String message, String name) {
        super(message);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
