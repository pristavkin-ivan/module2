package com.epam.esm.gift_certificate.exception;

public class NoSuchCertificateException extends Exception {

    public NoSuchCertificateException() {
        super();
    }

    public NoSuchCertificateException(String message) {
        super(message);
    }

    public NoSuchCertificateException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchCertificateException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
