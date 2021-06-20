package com.epam.esm.gift_certificate.controller;

import com.epam.esm.gift_certificate.exception.NoSuchCertificateException;
import com.epam.esm.gift_certificate.exception.NoSuchTagException;
import com.epam.esm.gift_certificate.exception.TagCreationException;
import com.epam.esm.gift_certificate.model.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping(consumes = "application/json", produces = "application/json")
public class AppExceptionHandler {

    private final static String CERTIFICATE_CODE = "01";

    private final static String TAG_CODE = "02";

    @ExceptionHandler(NoSuchCertificateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handleNoSuchCertificateException(NoSuchCertificateException exception) {
        return new ExceptionDto(HttpStatus.NOT_FOUND.value() + CERTIFICATE_CODE, exception.getMessage());
    }

    @ExceptionHandler(NoSuchTagException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handleNoSuchTagException(NoSuchTagException exception) {
        return new ExceptionDto(HttpStatus.NOT_FOUND.value() + TAG_CODE, exception.getMessage());
    }

    @ExceptionHandler(TagCreationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionDto handleTagCreationException(TagCreationException exception) {
        return new ExceptionDto(HttpStatus.CONFLICT.value() + TAG_CODE, exception.getMessage());
    }

}
