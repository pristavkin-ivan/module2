package com.epam.esm.gift_certificate.service.impl;

import com.epam.esm.gift_certificate.entity.Tag;
import com.epam.esm.gift_certificate.dao.api.Dao;
import com.epam.esm.gift_certificate.service.api.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class TagServiceImpl implements TagService {

    private final Dao<Tag> tagDao;

    @Autowired
    public TagServiceImpl(Dao<Tag> tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<Tag> readAll() {
        return tagDao.getAll();
    }

    @Override
    public Tag read(int id) {
        return tagDao.get(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        tagDao.delete(id);
    }

    @Override
    public void create(Tag tag) {
        tagDao.create(tag);
    }

}
