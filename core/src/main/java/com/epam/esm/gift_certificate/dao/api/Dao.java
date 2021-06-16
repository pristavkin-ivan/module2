package com.epam.esm.gift_certificate.dao.api;

import com.epam.esm.gift_certificate.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends Entity> {
    List<T> getAll();
    Optional<T> get(int id);
    default void update(int id, T entity){}
    void delete(int id);
    void create(T entity);
}
