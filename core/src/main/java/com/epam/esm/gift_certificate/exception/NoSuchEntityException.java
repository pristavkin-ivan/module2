package com.epam.esm.gift_certificate.exception;

public class NoSuchEntityException extends RuntimeException {

    private Integer id;

    public NoSuchEntityException() {
        super();
    }

    public NoSuchEntityException(String message, Integer id) {
        super(message);
        this.id = id;
    }

    public NoSuchEntityException(String message) {
        super(message);
    }

    public Integer getId() {
        return id;
    }
}
