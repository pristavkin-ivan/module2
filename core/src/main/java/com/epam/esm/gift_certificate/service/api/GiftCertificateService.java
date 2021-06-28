package com.epam.esm.gift_certificate.service.api;

import com.epam.esm.gift_certificate.exception.NoSuchTagException;
import com.epam.esm.gift_certificate.exception.TagCreationException;
import com.epam.esm.gift_certificate.exception.NoSuchCertificateException;
import com.epam.esm.gift_certificate.model.dto.GiftCertificateDto;
import com.epam.esm.gift_certificate.context.ParamContext;

import java.util.List;

/**
 * Service for operating with GiftCertificates
 */
public interface GiftCertificateService {

    /**
     * Retrieves list of GiftCertificates
     * Could retrieve by part of name, description with specified sorting
     * @param paramContext Context of retrieving params
     * @return List of GiftCertificateDto
     */
    List<GiftCertificateDto> readAllGiftCertificates(ParamContext paramContext);

    /**
     * Retrieves GiftCertificate by id
     * @param id GiftCertificate's id
     * @return GiftCertificateDto
     * @throws NoSuchCertificateException if gift certificate is not exist
     */
    GiftCertificateDto readGiftCertificate(int id) throws NoSuchCertificateException;

    /**
     * Saves gift certificate in db
     * @param giftCertificate
     * @return GiftCertificateDto
     * @throws NoSuchCertificateException if gift certificate is not exist
     * @throws NoSuchTagException if tag is not exist
     * @throws TagCreationException if there are any problems with tag creation
     */
    GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificate)
            throws NoSuchCertificateException, NoSuchTagException, TagCreationException;

    /**
     * Updates gift certificate in db
     * @param giftCertificate
     * @return * @return GiftCertificateDto
     * @throws NoSuchCertificateException if gift certificate is not exist
     * @throws NoSuchTagException if tag is not exist
     * @throws TagCreationException if there are any problems with tag creation
     */
    GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificate)
            throws NoSuchCertificateException, NoSuchTagException, TagCreationException;

    /**
     * Deletes gift certificate from db
     * @param id
     * @throws NoSuchCertificateException if gift certificate is not exist
     */
    void deleteGiftCertificate(int id) throws NoSuchCertificateException;
}
