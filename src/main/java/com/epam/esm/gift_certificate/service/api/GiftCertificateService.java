package com.epam.esm.gift_certificate.service.api;

import com.epam.esm.gift_certificate.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificate> readAllGiftCertificates();
    GiftCertificate readGiftCertificate(int id);
    void createGiftCertificate(GiftCertificate giftCertificate);
    void updateGiftCertificate(int id, GiftCertificate giftCertificate);
    void deleteGiftCertificate(int id);
}
