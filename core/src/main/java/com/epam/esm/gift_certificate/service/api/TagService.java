package com.epam.esm.gift_certificate.service.api;

import com.epam.esm.gift_certificate.exception.NoSuchTagException;
import com.epam.esm.gift_certificate.exception.TagCreationException;
import com.epam.esm.gift_certificate.model.dto.TagDto;

import java.util.List;

/**
 * Service for operating with Tags
 */
public interface TagService {
    /**
     * Retrieves all Tags
     * @return List of TagDto
     */
    List<TagDto> readAll();

    /**
     * Retrieves tag by id
     * @param id Tag's id
     * @return TagDto
     * @throws NoSuchTagException if tag is not exist
     */
    TagDto read(int id) throws NoSuchTagException;

    /**
     * Deletes tag by id
     * @param id Tag's id
     * @throws NoSuchTagException if tag is not exist
     */
    void delete(int id) throws NoSuchTagException;

    /**
     * Saves Tag in db
     * @param tag
     * @return TagDto
     * @throws NoSuchTagException if tag is not exist
     * @throws TagCreationException if there are any problems with tag creation
     */
    TagDto create(TagDto tag) throws NoSuchTagException, TagCreationException;
}
