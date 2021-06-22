package com.epam.esm.gift_certificate.exception;

public class NoSuchEntityException extends Exception {

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

    public NoSuchEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchEntityException(Throwable cause) {
        super(cause);
    }

    public Integer getId() {
        return id;
    }
}
