package com.epam.esm.gift_certificate.dao.api;

import com.epam.esm.gift_certificate.entity.Entity;
import com.epam.esm.gift_certificate.entity.Tag;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao<T extends Entity> {
    List<T> getAll();

    Optional<T> get(int id);

    void delete(int id);

    void create(T entity);

    void update(T entity);

    List<T> getAll(String tag);

    Optional<T> getLastRow();
}
