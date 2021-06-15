package com.epam.esm.gift_certificate.dao;

import com.epam.esm.gift_certificate.entity.Entity;
import com.epam.esm.gift_certificate.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends Entity> {

    List<T> getAll();
    Optional<T> get(int index);
    void update(int index, GiftCertificate entity);
    void delete(int index);
    void create(GiftCertificate giftCertificate);

}
