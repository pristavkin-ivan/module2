package com.epam.esm.gift_certificate.dao.api;

import com.epam.esm.gift_certificate.exception.NoSuchTagException;
import com.epam.esm.gift_certificate.exception.TagCreationException;
import com.epam.esm.gift_certificate.model.entity.Entity;
import com.epam.esm.gift_certificate.model.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao<T extends Entity> {
    List<T> getAll();

    Optional<T> get(int id) throws NoSuchTagException;

    void delete(int id) throws NoSuchTagException;

    Optional<T> create(T entity) throws NoSuchTagException, TagCreationException;

    Optional<Tag> getTagByName(String name) throws NoSuchTagException;

    List<T> getTagsByGiftCertificateId(int giftCertificateId);
}
