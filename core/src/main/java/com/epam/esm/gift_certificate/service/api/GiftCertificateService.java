package com.epam.esm.gift_certificate.service.api;

import com.epam.esm.gift_certificate.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificate> readAllGiftCertificates(String sortType);
    List<GiftCertificate> readAllGiftCertificatesByTag(String tag);
    GiftCertificate readGiftCertificate(int id);
    GiftCertificate createGiftCertificate(GiftCertificate giftCertificate);
    GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate);
    void deleteGiftCertificate(int id);
}
