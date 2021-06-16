package com.epam.esm.gift_certificate.service.api;

import com.epam.esm.gift_certificate.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> readAll();
    Tag read(int id);
    void delete(int id);
    void create(Tag tag);
}
