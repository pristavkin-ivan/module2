package com.epam.esm.gift_certificate.dao.api;

import com.epam.esm.gift_certificate.exception.NoSuchCertificateException;
import com.epam.esm.gift_certificate.model.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

/**
 * Dao for operating with Gift certificates database
 *
 * @param <T> Any implementations of GiftCertificate entity
 */
public interface GiftCertificateDao<T extends GiftCertificate> {

    /**
     * Retrieves list of GiftCertificates from database
     * @param query sql query
     * @param words list of searching words
     * @return List of GiftCertificates
     */
    List<T> getAll(String query, List<String> words);

    /**
     * Retrieves list of GiftCertificates from database
     * @param tag searching tag
     * @return List of GiftCertificates
     */
    List<T> getAll(String tag);

    /**
     * Retrieves GiftCertificate by id from database
     * @param id
     * @return Optional of GiftCertificate
     * @throws NoSuchCertificateException if gift certificate is not exist
     */
    Optional<T> get(int id) throws NoSuchCertificateException;

    /**
     * Deletes GiftCertificate by id from database
     * @param id
     * @throws NoSuchCertificateException if gift certificate is not exist
     */
    void delete(int id) throws NoSuchCertificateException;

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
