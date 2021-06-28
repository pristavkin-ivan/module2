package com.epam.esm.gift_certificate.dao.api;

import com.epam.esm.gift_certificate.exception.NoSuchTagException;
import com.epam.esm.gift_certificate.exception.TagCreationException;
import com.epam.esm.gift_certificate.model.entity.Entity;
import com.epam.esm.gift_certificate.model.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * Dao for operating with Tag database
 *
 * @param <T> Any implementations of Tag entity
 */
public interface TagDao<T extends Tag> {
    /**
     * Retrieves list of tags from db
     * @return List of Tags
     */
    List<T> getAll();

    /**
     * Retrieves tag by id from db
     * @param id
     * @return Optional of Tag
     * @throws NoSuchTagException if tag is not exist
     */
    T get(int id) throws NoSuchTagException;

    /**
     * Deletes tag by id from db
     * @param id
     * @throws NoSuchTagException if tag is not exist
     */
    void delete(int id) throws NoSuchTagException;

    /**
     * Saves tag in db
     * @param entity
     * @return Optional of Tag
     * @throws NoSuchTagException if tag is not exist
     * @throws TagCreationException if there are any problems with saving
     */
    T create(T entity) throws NoSuchTagException, TagCreationException;

    /**
     * Retrieves tag by name from db
     * @param name
     * @return Optional of Tag
     * @throws NoSuchTagException
     */
    T getTagByName(String name) throws NoSuchTagException;

    /**
     * Retrieves list of tags from db by GiftCertificate id
     * @param giftCertificateId
     * @return List of tags
     */
    List<T> getTagsByGiftCertificateId(int giftCertificateId);
}
