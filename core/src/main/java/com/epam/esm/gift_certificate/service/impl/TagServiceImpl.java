package com.epam.esm.gift_certificate.service.impl;

import com.epam.esm.gift_certificate.dao.api.TagDao;
import com.epam.esm.gift_certificate.exception.NoSuchTagException;
import com.epam.esm.gift_certificate.exception.TagCreationException;
import com.epam.esm.gift_certificate.model.dto.TagDto;
import com.epam.esm.gift_certificate.model.entity.Tag;
import com.epam.esm.gift_certificate.service.api.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public final class TagServiceImpl implements TagService {

    private final TagDao<Tag> tagDao;

    @Autowired
    public TagServiceImpl(TagDao<Tag> tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<TagDto> readAll() {
        return tagDao.getAll().stream().map(this::convertToTagDto).collect(Collectors.toList());
    }

    @Override
    public TagDto read(int id) {
        return convertToTagDto(tagDao.get(id));
    }

    @Override
    public void delete(int id) {
        tagDao.delete(id);
    }

    @Override
    public TagDto create(TagDto tag) {
        return convertToTagDto(tagDao.create(convertToTag(tag)));
    }

    private TagDto convertToTagDto(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }

    private Tag convertToTag(TagDto tag) {
        return new Tag(tag.getId(), tag.getName());
    }

}
