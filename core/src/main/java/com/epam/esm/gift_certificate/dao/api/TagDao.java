package com.epam.esm.gift_certificate.dao.api;

import com.epam.esm.gift_certificate.entity.Entity;
import com.epam.esm.gift_certificate.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao<T extends Entity> {
    List<T> getAll();

    Optional<T> get(int id);

    void delete(int id);

    void create(T entity);

    Optional<Tag> getTagByName(String name);

    List<T> getTagList(int id);
}
