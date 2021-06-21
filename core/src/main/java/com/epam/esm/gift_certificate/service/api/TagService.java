package com.epam.esm.gift_certificate.service.api;

import com.epam.esm.gift_certificate.exception.NoSuchTagException;
import com.epam.esm.gift_certificate.exception.TagCreationException;
import com.epam.esm.gift_certificate.model.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> readAll();

    Tag read(int id) throws NoSuchTagException;

    void delete(int id) throws NoSuchTagException;

    Tag create(Tag tag) throws NoSuchTagException, TagCreationException;
}
