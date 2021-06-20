package com.epam.esm.gift_certificate.service.impl;

import com.epam.esm.gift_certificate.dao.api.GiftCertificateDao;
import com.epam.esm.gift_certificate.model.dto.GiftCertificateDto;
import com.epam.esm.gift_certificate.dao.api.GiftsTagsDao;
import com.epam.esm.gift_certificate.dao.api.TagDao;
import com.epam.esm.gift_certificate.exception.NoSuchTagException;
import com.epam.esm.gift_certificate.exception.TagCreationException;
import com.epam.esm.gift_certificate.model.entity.GiftCertificate;
import com.epam.esm.gift_certificate.model.entity.Tag;
import com.epam.esm.gift_certificate.exception.NoSuchCertificateException;
import com.epam.esm.gift_certificate.service.api.GiftCertificateService;
import com.epam.esm.gift_certificate.util.IsoDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao<GiftCertificate> giftCertificateDao;

    private final TagDao<Tag> tagDao;

    private final GiftsTagsDao giftsTagsDao;

    private final static String NAME_FIELD = "name";

    private final static String DESCRIPTION_FIELD = "description";

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao<GiftCertificate> giftCertificateDao, TagDao<Tag> tagDao
            , GiftsTagsDao giftsTagsDao) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.giftsTagsDao = giftsTagsDao;
    }

    @Override
    public List<GiftCertificateDto> readAllGiftCertificates(GetAllState getAllState) {
        ArrayList<String> words = null;
        final Collection<String> searchWords = getAllState.getSearchMap().values();

        if (!searchWords.isEmpty()) {
            words = new ArrayList<>(searchWords);
        }

        final List<GiftCertificate> giftCertificates = giftCertificateDao.getAll(resolveQuery(getAllState), words);
        final List<GiftCertificateDto> dtos = giftCertificates.stream().map(this::mapToDto).collect(Collectors.toList());

        dtos.forEach((certificate)
                -> certificate.setTags(tagDao.getTagsByGiftCertificateId(certificate.getId())));

        return dtos;
    }

    @Override
    public List<GiftCertificateDto> readAllGiftCertificatesByTag(String tag) {
        final List<GiftCertificate> giftCertificates = giftCertificateDao.getAll(tag);

        final List<GiftCertificateDto> dtos = giftCertificates.stream().map(this::mapToDto).collect(Collectors.toList());

        dtos.forEach((certificate)
                -> certificate.setTags(tagDao.getTagsByGiftCertificateId(certificate.getId())));

        return dtos;
    }

    @Override
    public GiftCertificateDto readGiftCertificate(int id) throws NoSuchCertificateException {
        final Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.get(id);
        GiftCertificateDto dto = null;

        if (giftCertificateOptional.isPresent()) {
            dto = mapToDto(giftCertificateOptional.get());
            dto.setTags(tagDao.getTagsByGiftCertificateId(id));
        }

        return dto;
    }

    @Override
    @SuppressWarnings("all")
    @Transactional
    public GiftCertificateDto createGiftCertificate(GiftCertificateDto certificateDto)
            throws NoSuchCertificateException, NoSuchTagException, TagCreationException {

        final List<Tag> tags = certificateDto.getTags();

        giftCertificateDao.create(mapToEntity(certificateDto));

        final Integer newId = giftCertificateDao.getLastRow().get().getId();

        if (tags != null) {
            populateTagsCreate(newId, tags);
        }

        return readGiftCertificate(newId);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateGiftCertificate(GiftCertificateDto certificateDto)
            throws NoSuchCertificateException, NoSuchTagException, TagCreationException {

        final List<Tag> tags = certificateDto.getTags();

        giftCertificateDao.update(mapToEntity(certificateDto));

        if (tags != null) {
            populateTagsUpdate(certificateDto.getId(), tags);
        }

        return readGiftCertificate(certificateDto.getId());
    }

    @Override
    public void deleteGiftCertificate(int id) throws NoSuchCertificateException {
        giftCertificateDao.delete(id);
    }

    @SuppressWarnings("all")
    private void populateTagsCreate(int giftCertificateId, List<Tag> insertedTags)
            throws NoSuchTagException, TagCreationException {

        List<Tag> allTags = tagDao.getAll();

        for (Tag insertedTag : insertedTags) {
            int newTagId = processNewTags(allTags, insertedTag);

            if (newTagId == 0) {
                newTagId = allTags.stream()
                        .filter(tag -> tag.getName().equals(insertedTag.getName())).findAny().get().getId();
            }

            giftsTagsDao.createAssociation(giftCertificateId, newTagId);
        }
    }

    private void populateTagsUpdate(int giftCertificateId, List<Tag> insertedTags)
            throws NoSuchTagException, TagCreationException {

        List<Tag> actualTags = tagDao.getTagsByGiftCertificateId(giftCertificateId);
        List<Tag> allTags = tagDao.getAll();

        for (Tag insertedTag : insertedTags) {
            int newTagId = processNewTags(allTags, insertedTag);

            if (newTagId == 0) {
                newTagId = allTags.stream()
                        .filter(tag -> tag.getName().equals(insertedTag.getName())).findAny().get().getId();
            }

            insertAssociation(actualTags, insertedTag, giftCertificateId, newTagId);
        }

        processDeletingTags(giftCertificateId, insertedTags, actualTags);
    }

    private void processDeletingTags(int giftCertificateId, List<Tag> insertedTags, List<Tag> actualTags) {
        for (Tag actualTag : actualTags) {
            int tag_id = actualTag.getId();

            if (insertedTags.stream()
                    .noneMatch(giftCertificate -> giftCertificate.getName().equals(actualTag.getName()))) {
                giftsTagsDao.deleteAllAssociationsById(giftCertificateId, tag_id);
            }
        }
    }

    private int processNewTags(List<Tag> allTags, Tag insertedTag) throws NoSuchTagException, TagCreationException {
        if (allTags.stream().noneMatch(tag -> tag.getName().equals(insertedTag.getName()))) {
            return tagDao.create(insertedTag).orElse(insertedTag).getId();
        }
        return 0;
    }

    private void insertAssociation(List<Tag> actualTags, Tag insertedTag, int giftCertificateId, int tag_id) {
        if (actualTags.stream().noneMatch(tag -> tag.getName().equals(insertedTag.getName()))) {
            giftsTagsDao.createAssociation(giftCertificateId, tag_id);
        }
    }

    private GiftCertificateDto mapToDto(GiftCertificate certificate) {
        return new GiftCertificateDto(certificate.getId()
                , certificate.getName()
                , certificate.getDescription()
                , certificate.getPrice()
                , certificate.getDuration()
                , IsoDateUtil.getCurrentTimeInIsoFormat(certificate.getCreateDate())
                , IsoDateUtil.getCurrentTimeInIsoFormat(certificate.getLastUpdateDate())
                , new ArrayList<>());
    }

    private GiftCertificate mapToEntity(GiftCertificateDto certificate) {
        return new GiftCertificate(certificate.getId()
                , certificate.getName()
                , certificate.getDescription()
                , certificate.getPrice()
                , certificate.getDuration()
                , null
                , null
                , new ArrayList<>());
    }

    private String resolveQuery(GetAllState getAllState) {
        StringBuffer query = new StringBuffer(GetAllParts.select);

        searchLogic(getAllState, query);
        sortLogic(getAllState, GetAllParts.orderBy, query);

        return query.toString();
    }

    private void sortLogic(GetAllState getAllState, String orderBy, StringBuffer query) {
        if (!getAllState.getSortTypes().isEmpty()) {
            query.append(orderBy).append(getAllState.getSortTypes().get(0));
            getAllState.getSortTypes().stream().skip(1).forEach((sortType) -> query.append(", ").append(sortType));
        }
    }

    private void searchLogic(GetAllState getAllState, StringBuffer query) {

        if (!getAllState.getSearchMap().isEmpty()) {
            query.append(GetAllParts.where);

            if (getAllState.getSearchMap().get(NAME_FIELD) != null) {
                query.append(GetAllParts.locateName);
            }

            if (getAllState.getSearchMap().get(DESCRIPTION_FIELD) != null) {
                if (getAllState.getSearchMap().size() > 1) {
                    query.append(GetAllParts.and);
                }
                query.append(GetAllParts.locateDescription);
            }

        }
    }

}
