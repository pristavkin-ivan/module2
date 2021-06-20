package com.epam.esm.gift_certificate.dao.api;

import com.epam.esm.gift_certificate.model.entity.Entity;
import com.epam.esm.gift_certificate.exception.NoSuchCertificateException;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao<T extends Entity> {

    List<T> getAll(String query, List<String> words);

    List<T> getAll(String tag);

    Optional<T> get(int id) throws NoSuchCertificateException;

    void delete(int id) throws NoSuchCertificateException;

    void create(T entity);

    void update(T entity) throws NoSuchCertificateException;

    Optional<T> getLastRow();
}
