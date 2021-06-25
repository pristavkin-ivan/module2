package com.epam.esm.gift_certificate.exception;

public class NoSuchCertificateException extends NoSuchEntityException {

    public NoSuchCertificateException(String message, Integer id) {
        super(message, id);
    }

    public NoSuchCertificateException() {
        super();
    }

}
