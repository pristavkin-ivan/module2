package com.epam.esm.gift_certificate.service.api;

import com.epam.esm.gift_certificate.exception.NoSuchTagException;
import com.epam.esm.gift_certificate.exception.TagCreationException;
import com.epam.esm.gift_certificate.exception.NoSuchCertificateException;
import com.epam.esm.gift_certificate.model.dto.GiftCertificateDto;
import com.epam.esm.gift_certificate.service.impl.GetAllState;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificateDto> readAllGiftCertificates(GetAllState getAllState);

    List<GiftCertificateDto> readAllGiftCertificatesByTag(String tag);

    GiftCertificateDto readGiftCertificate(int id) throws NoSuchCertificateException;

    GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificate)
            throws NoSuchCertificateException, NoSuchTagException, TagCreationException;

    GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificate)
            throws NoSuchCertificateException, NoSuchTagException, TagCreationException;

    void deleteGiftCertificate(int id) throws NoSuchCertificateException;
}
