package com.epam.esm.gift_certificate.service.impl;

import com.epam.esm.gift_certificate.context.ParamContext;
import com.epam.esm.gift_certificate.dao.impl.QueryParts;
import com.epam.esm.gift_certificate.dao.api.GiftCertificateDao;
import com.epam.esm.gift_certificate.model.dto.GiftCertificateDto;
import com.epam.esm.gift_certificate.dao.api.GiftsTagsDao;
import com.epam.esm.gift_certificate.dao.api.TagDao;
import com.epam.esm.gift_certificate.model.dto.TagDto;
import com.epam.esm.gift_certificate.model.entity.GiftCertificate;
import com.epam.esm.gift_certificate.model.entity.Tag;
import com.epam.esm.gift_certificate.service.api.GiftCertificateService;
import com.epam.esm.gift_certificate.util.IsoDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao<GiftCertificate> giftCertificateDao;

    private final TagDao<Tag> tagDao;

    private final GiftsTagsDao giftsTagsDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao<GiftCertificate> giftCertificateDao, TagDao<Tag> tagDao
            , GiftsTagsDao giftsTagsDao) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.giftsTagsDao = giftsTagsDao;
    }

    @Override
    public List<GiftCertificateDto> readAllGiftCertificates(ParamContext paramContext) {
        final List<GiftCertificate> giftCertificates = giftCertificateDao.getAll(paramContext);
        final List<GiftCertificateDto> dtos = giftCertificates.stream().map(this::mapToDto).collect(Collectors.toList());

        dtos.forEach((certificate)
                -> certificate.setTags(tagDao
                .getTagsByGiftCertificateId(certificate.getId()).stream()
                .map(this::convertToTagDto).collect(Collectors.toList())));

        return dtos;
    }

    @Override
    public GiftCertificateDto readGiftCertificate(int id) {
        final GiftCertificate giftCertificate = giftCertificateDao.get(id);
        GiftCertificateDto dto;

        dto = mapToDto(giftCertificate);
        dto.setTags(tagDao.getTagsByGiftCertificateId(id).stream()
                .map(this::convertToTagDto).collect(Collectors.toList()));

        return dto;
    }

    @Override
    @Transactional
    public GiftCertificateDto createGiftCertificate(GiftCertificateDto certificateDto) {
        final List<TagDto> tags = certificateDto.getTags();
        final Integer newId = giftCertificateDao.create(mapToEntity(certificateDto));

        if (tags != null) {
            populateTagsCreate(newId, tags.stream().map(this::convertToTag).collect(Collectors.toList()));
        }

        return readGiftCertificate(newId);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateGiftCertificate(GiftCertificateDto certificateDto) {
        final List<TagDto> tags = certificateDto.getTags();
        GiftCertificate modifyingGiftCertificate = giftCertificateDao.get(certificateDto.getId());

        updateLogic(modifyingGiftCertificate, certificateDto);

        giftCertificateDao.update(modifyingGiftCertificate);

        if (tags != null) {
            populateTagsUpdate(certificateDto.getId(), tags.stream().map(this::convertToTag)
                    .collect(Collectors.toList()));
        }

        return readGiftCertificate(certificateDto.getId());
    }

    @Override
    public void deleteGiftCertificate(int id) {
        giftCertificateDao.delete(id);
    }

    private void populateTagsCreate(int giftCertificateId, List<Tag> insertedTags) {
        List<Tag> allTags = tagDao.getAll();

        for (Tag insertedTag : insertedTags) {
            int newTagId = createTagIfItIsNotExist(allTags, insertedTag);

            newTagId = getNewTagId(allTags, insertedTag, newTagId);

            giftsTagsDao.createAssociation(giftCertificateId, newTagId);
        }
    }

    private void populateTagsUpdate(int giftCertificateId, List<Tag> insertedTags) {
        List<Tag> actualTags = tagDao.getTagsByGiftCertificateId(giftCertificateId);
        List<Tag> allTags = tagDao.getAll();

        for (Tag insertedTag : insertedTags) {
            int newTagId = createTagIfItIsNotExist(allTags, insertedTag);

            newTagId = getNewTagId(allTags, insertedTag, newTagId);

            insertAssociation(actualTags, insertedTag, giftCertificateId, newTagId);
        }

        processDeletingTags(giftCertificateId, insertedTags, actualTags);
    }

    @SuppressWarnings("all")
    private int getNewTagId(List<Tag> allTags, Tag insertedTag, int newTagId) {
        if (newTagId == 0) {
            newTagId = allTags.stream()
                    .filter(tag -> tag.getName().equals(insertedTag.getName())).findAny().get().getId();
        }
        return newTagId;
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

    private int createTagIfItIsNotExist(List<Tag> allTags, Tag insertedTag) {
        if (allTags.stream().noneMatch(tag -> tag.getName().equals(insertedTag.getName()))) {
            return tagDao.create(insertedTag).getId();
        }
        return 0;
    }

    private void insertAssociation(List<Tag> actualTags, Tag insertedTag, int giftCertificateId, int tagId) {
        if (actualTags.stream().noneMatch(tag -> tag.getName().equals(insertedTag.getName()))) {
            giftsTagsDao.createAssociation(giftCertificateId, tagId);
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
                , null);
    }

    private void updateLogic(GiftCertificate modifyingGiftCertificate, GiftCertificateDto giftCertificate) {
        if (giftCertificate.getName() != null) {
            modifyingGiftCertificate.setName(giftCertificate.getName());
        }

        if (giftCertificate.getDescription() != null) {
            modifyingGiftCertificate.setDescription(giftCertificate.getDescription());
        }

        if (giftCertificate.getPrice() != null) {
            modifyingGiftCertificate.setPrice(giftCertificate.getPrice());
        }

        if (giftCertificate.getDuration() != null) {
            modifyingGiftCertificate.setDuration(giftCertificate.getDuration());
        }
    }

    private TagDto convertToTagDto(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }

    private Tag convertToTag(TagDto tag) {
        return new Tag(tag.getId(), tag.getName());
    }

}
