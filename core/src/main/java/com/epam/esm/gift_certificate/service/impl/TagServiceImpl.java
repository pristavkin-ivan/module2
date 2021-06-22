package com.epam.esm.gift_certificate.service.impl;

import com.epam.esm.gift_certificate.dao.api.TagDao;
import com.epam.esm.gift_certificate.exception.NoSuchTagException;
import com.epam.esm.gift_certificate.exception.TagCreationException;
import com.epam.esm.gift_certificate.model.entity.Tag;
import com.epam.esm.gift_certificate.service.api.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class TagServiceImpl implements TagService {

    private final TagDao<Tag> tagDao;

    @Autowired
    public TagServiceImpl(TagDao<Tag> tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<Tag> readAll() {
        return tagDao.getAll();
    }

    @Override
    @SuppressWarnings("all")
    public Tag read(int id) throws NoSuchTagException {
        return tagDao.get(id).get();
    }

    @Override
    public void delete(int id) throws NoSuchTagException {
        tagDao.delete(id);
    }

    @Override
    @SuppressWarnings("all")
    public Tag create(Tag tag) throws NoSuchTagException, TagCreationException {
        return tagDao.create(tag).get();
    }

}
