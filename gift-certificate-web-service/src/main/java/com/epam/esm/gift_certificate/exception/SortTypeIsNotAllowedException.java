package com.epam.esm.gift_certificate.exception;

public class SortTypeIsNotAllowedException extends RuntimeException {

    public SortTypeIsNotAllowedException() {
        super();
    }

    public SortTypeIsNotAllowedException(String message) {
        super(message);
    }

}
