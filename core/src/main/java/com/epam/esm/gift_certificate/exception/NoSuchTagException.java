package com.epam.esm.gift_certificate.exception;

public class NoSuchTagException extends NoSuchEntityException {

    private String name;

    public NoSuchTagException() {
        super();
    }

    public NoSuchTagException(String message, Integer id) {
        super(message, id);
    }

    public NoSuchTagException(String message, String name) {
        super(message);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
