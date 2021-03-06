package com.epam.esm.gift_certificate.controller;

import com.epam.esm.gift_certificate.model.dto.GiftCertificateDto;
import com.epam.esm.gift_certificate.service.api.GiftCertificateService;
import com.epam.esm.gift_certificate.context.ParamContext;
import com.epam.esm.gift_certificate.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/gift-certificates", consumes = "application/json", produces = "application/json")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    private final Validation validation;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, Validation validation) {
        this.giftCertificateService = giftCertificateService;
        this.validation = validation;
    }

    @GetMapping
    public List<GiftCertificateDto> getAllGiftCertificates(
            @RequestParam(value = "sort", required = false) String[] sortTypes
            , @RequestParam(value = "name", required = false) String name
            , @RequestParam(value = "description", required = false) String description
            , @RequestParam(value = "tag", required = false) String tagName) {

        HashMap<String, String> searchMap = new HashMap<>();
        List<String> sortTypesList = new ArrayList<>();

        configureSearchingMap(searchMap, name, description, tagName);
        sortTypesList = configureSortTypesList(sortTypes, sortTypesList);
        validation.validate(sortTypesList);

        return giftCertificateService.readAllGiftCertificates(new ParamContext(searchMap, sortTypesList));
    }

    @GetMapping("/{id}")
    public GiftCertificateDto getGiftCertificate(@PathVariable("id") int id) {
        return giftCertificateService.readGiftCertificate(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto addGiftCertificate(@RequestBody GiftCertificateDto giftCertificate) {

        return giftCertificateService.createGiftCertificate(giftCertificate);
    }

    @PutMapping("/{id}")
    public GiftCertificateDto updateNameOfGiftCertificate(@PathVariable("id") int id
            , @RequestBody GiftCertificateDto giftCertificate) {

        giftCertificate.setId(id);
        return giftCertificateService.updateGiftCertificate(giftCertificate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNameOfGiftCertificate(@PathVariable("id") int id) {
        giftCertificateService.deleteGiftCertificate(id);
    }

    private void configureSearchingMap(Map<String, String> searchMap, String name, String description, String tagName) {
        if (name != null) {
            searchMap.put("name", name);
        }
        if (description != null) {
            searchMap.put("description", description);
        }
        if (tagName != null) {
            searchMap.put("tag", tagName);
        }
    }

    private List<String> configureSortTypesList(String[] sortTypes, List<String> sortTypesList) {
        if (sortTypes != null) {
            sortTypesList = Arrays.asList(sortTypes);
        }
        return sortTypesList;
    }
}
