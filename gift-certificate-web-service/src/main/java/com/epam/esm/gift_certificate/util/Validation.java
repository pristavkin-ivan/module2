package com.epam.esm.gift_certificate.util;

import com.epam.esm.gift_certificate.exception.InvalidInputException;

import java.util.Arrays;
import java.util.List;

public class Validation {

    private final static List<String> AVAILABLE_SORT_TYPES
            = Arrays.asList("name asc", "name desc", "date asc", "date desc");

    public static void validate(List<String> sortTypesList) throws InvalidInputException {
        for (String sortType : sortTypesList) {
            if (AVAILABLE_SORT_TYPES.stream().noneMatch(sort -> sort.equalsIgnoreCase(sortType))) {
                throw new InvalidInputException("invalid input");
            }
        }
    }
}
