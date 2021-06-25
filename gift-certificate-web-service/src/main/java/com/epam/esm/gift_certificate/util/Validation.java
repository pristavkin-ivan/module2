package com.epam.esm.gift_certificate.util;

import com.epam.esm.gift_certificate.exception.SortTypeIsNotAllowedException;

import java.util.Arrays;
import java.util.List;

public class Validation {

    private final List<String> availableSortTypesArray;

    public Validation(String availableSortTypes) {
        availableSortTypesArray = Arrays.asList(availableSortTypes.split(","));
    }

    public void validate(List<String> sortTypesList) throws SortTypeIsNotAllowedException {
        for (String sortType : sortTypesList) {
            if (availableSortTypesArray.stream().noneMatch(sort -> sort.equalsIgnoreCase(sortType))) {
                throw new SortTypeIsNotAllowedException("Not allowed sort type");
            }
        }
    }
}