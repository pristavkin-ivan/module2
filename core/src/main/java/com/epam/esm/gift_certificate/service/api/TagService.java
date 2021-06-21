package com.epam.esm.gift_certificate.service.api;

import com.epam.esm.gift_certificate.exception.NoSuchTagException;
import com.epam.esm.gift_certificate.exception.TagCreationException;
import com.epam.esm.gift_certificate.model.entity.Tag;

import java.util.List;

/**
 * Service for operating with Tags
 */
public interface TagService {
    /**
     * Retrieves all Tags
     * @return List of Tags
     */
    List<Tag> readAll();

    /**
     * Retrieves tag by id
     * @param id Tag's id
     * @return Tag
     * @throws NoSuchTagException if tag is not exist
     */
    Tag read(int id) throws NoSuchTagException;

    /**
     * Deletes tag by id
     * @param id Tag's id
     * @throws NoSuchTagException if tag is not exist
     */
    void delete(int id) throws NoSuchTagException;

    /**
     * Saves Tag in db
     * @param tag
     * @return Tag
     * @throws NoSuchTagException if tag is not exist
     * @throws TagCreationException if there are any problems with tag creation
     */
    Tag create(Tag tag) throws NoSuchTagException, TagCreationException;
}
