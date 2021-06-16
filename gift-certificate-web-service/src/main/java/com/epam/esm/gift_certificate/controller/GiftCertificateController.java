package com.epam.esm.gift_certificate.controller;

import com.epam.esm.gift_certificate.entity.GiftCertificate;
import com.epam.esm.gift_certificate.service.api.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequestMapping(value = "/gift-certificates", consumes = "application/json", produces = "application/json")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    @ResponseBody
    public List<GiftCertificate> getAllGiftCertificates() {
        return giftCertificateService.readAllGiftCertificates();
    }

    @GetMapping ("/{id}")
    @ResponseBody
    public GiftCertificate getGiftCertificate(@PathVariable("id") int id) {
        return giftCertificateService.readGiftCertificate(id);
    }

    @GetMapping("/tag/{tag}")
    @ResponseBody
    public List<GiftCertificate> getAllGiftCertificates(@PathVariable("tag") String tag) {
        return giftCertificateService.readAllGiftCertificates(tag);
    }


    @PostMapping
    @ResponseBody
    public GiftCertificate addGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.createGiftCertificate(giftCertificate);
        //todo вернуть id
        return giftCertificate;
    }

    @PutMapping( "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateNameOfGiftCertificate(@PathVariable("id") int id, @RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.updateGiftCertificate(id, giftCertificate);
    }

    @DeleteMapping( "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNameOfGiftCertificate(@PathVariable("id") int id) {
        giftCertificateService.deleteGiftCertificate(id);
    }

}
