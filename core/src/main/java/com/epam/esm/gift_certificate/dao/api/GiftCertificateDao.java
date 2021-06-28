package com.epam.esm.gift_certificate.dao.api;

import com.epam.esm.gift_certificate.exception.NoSuchCertificateException;
import com.epam.esm.gift_certificate.model.entity.GiftCertificate;
import com.epam.esm.gift_certificate.context.ParamContext;

import java.util.List;

/**
 * Dao for operating with Gift certificates database
 *
 * @param <T> Any implementations of GiftCertificate entity
 */
public interface GiftCertificateDao<T extends GiftCertificate> {

    /**
     * Retrieves list of GiftCertificates from database
     * @param paramContext
     * @return List of GiftCertificates
     */
    List<T> getAll(ParamContext paramContext);

    /**
     * Retrieves GiftCertificate by id from database
     * @param id
     * @return GiftCertificate
     * @throws NoSuchCertificateException if gift certificate is not exist
     */
     T get(int id) throws NoSuchCertificateException;

    /**
     * Deletes GiftCertificate by id from database
     * @param id
     */
    void delete(int id);

    /**
     * Saves GiftCertificate in database
     * @param entity
     * @return
     */
    Integer create(T entity);

    /**
     *
     * @param entity with updated params
     * @throws NoSuchCertificateException if gift certificate is not exist
     */
    void update(T entity) throws NoSuchCertificateException;
}
