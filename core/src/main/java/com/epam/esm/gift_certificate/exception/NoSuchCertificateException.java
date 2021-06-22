package com.epam.esm.gift_certificate.exception;

public class NoSuchCertificateException extends NoSuchEntityException {

    public NoSuchCertificateException(String message, Integer id) {
        super(message, id);
    }

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
}
