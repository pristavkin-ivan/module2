package com.epam.esm.gift_certificate.controller;

import com.epam.esm.gift_certificate.exception.SortTypeIsNotAllowedException;
import com.epam.esm.gift_certificate.exception.NoSuchCertificateException;
import com.epam.esm.gift_certificate.exception.NoSuchTagException;
import com.epam.esm.gift_certificate.exception.TagCreationException;
import com.epam.esm.gift_certificate.model.dto.ExceptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class AppExceptionHandler {

    private final MessageSource messageSource;

    private final static String COMMON_CODE = "00";

    private final static String SORT_CODE = "03";

    private final static String CERTIFICATE_CODE = "01";

    private final static String TAG_CODE = "02";

    @Autowired
    public AppExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(NoSuchCertificateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handleNoSuchCertificateException(NoSuchCertificateException exception, Locale locale) {
        String errorMessage = messageSource.getMessage("noCertificate", null, "Unknown error"
                , locale);

        return new ExceptionDto(HttpStatus.NOT_FOUND.value() + CERTIFICATE_CODE, errorMessage
                + exception.getId());
    }

    @ExceptionHandler(SortTypeIsNotAllowedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleInvalidInputException(Locale locale) {
        String errorMessage = messageSource.getMessage("notAllowedSort", null, "Unknown error"
                , locale);

        return new ExceptionDto(HttpStatus.BAD_REQUEST.value() + SORT_CODE, errorMessage);
    }

    @ExceptionHandler(NoSuchTagException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handleNoSuchTagException(NoSuchTagException exception, Locale locale) {
        String errorMessage = messageSource.getMessage("noTag", null, "Unknown error"
                , locale);

        return new ExceptionDto(HttpStatus.NOT_FOUND.value() + TAG_CODE, errorMessage
                + exception.getId());
    }

    @ExceptionHandler(TagCreationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionDto handleTagCreationException(TagCreationException exception, Locale locale) {
        String errorMessage = messageSource.getMessage("suchTagExists", null, "Unknown error"
                , locale);

        return new ExceptionDto(HttpStatus.CONFLICT.value() + TAG_CODE, errorMessage
                + exception.getName());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleBadRequest(Locale locale) {
        String errorMessage = messageSource.getMessage("badRequest", null, "Unknown error"
                , locale);

        return new ExceptionDto(HttpStatus.BAD_REQUEST.value() + COMMON_CODE, errorMessage);
    }

}
