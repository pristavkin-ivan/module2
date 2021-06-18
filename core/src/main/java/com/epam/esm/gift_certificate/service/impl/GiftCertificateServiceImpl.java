package com.epam.esm.gift_certificate.service.impl;

import com.epam.esm.gift_certificate.dao.api.GiftCertificateDao;
import com.epam.esm.gift_certificate.dao.api.GiftsTagsDao;
import com.epam.esm.gift_certificate.dao.api.TagDao;
import com.epam.esm.gift_certificate.entity.GiftCertificate;
import com.epam.esm.gift_certificate.entity.Tag;
import com.epam.esm.gift_certificate.service.api.GiftCertificateService;
import com.epam.esm.gift_certificate.util.IsoDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
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
    public List<GiftCertificate> readAllGiftCertificates(String sortType) {
        final List<GiftCertificate> giftCertificates = giftCertificateDao.getAll();

        giftCertificates.forEach((giftCertificate)
                -> giftCertificate.setTags(tagDao.getTagsByGiftCertificateId(giftCertificate.getId())));

        return giftCertificates.stream().sorted(chooseComparator(sortType)).collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificate> readAllGiftCertificatesByTag(String tag) {
        final List<GiftCertificate> giftCertificates = giftCertificateDao.getAll(tag);

        giftCertificates.forEach((giftCertificate)
                -> giftCertificate.setTags(tagDao.getTagsByGiftCertificateId(giftCertificate.getId())));

        return giftCertificates;
    }

    @Override
    public GiftCertificate readGiftCertificate(int id) {
        final Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.get(id);
        GiftCertificate giftCertificate = null;

        if (giftCertificateOptional.isPresent()) {
            giftCertificate = giftCertificateOptional.get();
            giftCertificate.setTags(tagDao.getTagsByGiftCertificateId(id));
        }

        return giftCertificate;
    }

    //todo context holder?
    @Override
    @Transactional
    public GiftCertificate createGiftCertificate(GiftCertificate giftCertificate) {
        final String time = IsoDateUtil.getCurrentTimeInIsoFormat();
        final List<Tag> tags = giftCertificate.getTags();

        giftCertificate.setCreateDate(time);
        giftCertificate.setLastUpdateDate(time);
        giftCertificateDao.create(giftCertificate);

        final Integer newId = giftCertificateDao.getLastRow().orElse(giftCertificate).getId();

        if (tags != null) {
            populateTagsCreate(newId, tags);
        }

        return readGiftCertificate(newId);
    }

    @Override
    @Transactional
    public GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate) {
        final String lastModifiedTime = IsoDateUtil.getCurrentTimeInIsoFormat();
        final List<Tag> tags = giftCertificate.getTags();

        giftCertificate.setLastUpdateDate(lastModifiedTime);
        giftCertificateDao.update(giftCertificate);

        if (tags != null) {
            populateTagsUpdate(giftCertificate.getId(), tags);
        }

        return readGiftCertificate(giftCertificate.getId());
    }

    @Override
    public void deleteGiftCertificate(int id) {
        giftCertificateDao.delete(id);
    }

    @SuppressWarnings("all")
    private void populateTagsCreate(int giftCertificateId, List<Tag> insertedTags) {
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

    private void populateTagsUpdate(int giftCertificateId, List<Tag> insertedTags) {
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

    private int processNewTags(List<Tag> allTags, Tag insertedTag) {
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


    private int compareByNameDesc(GiftCertificate giftCertificate1, GiftCertificate giftCertificate2) {
        return giftCertificate1.getName().compareTo(giftCertificate2.getName());
    }

    private int compareByNameAsc(GiftCertificate giftCertificate1, GiftCertificate giftCertificate2) {
        return giftCertificate2.getName().compareTo(giftCertificate1.getName());
    }

    private int compareByDateDesc(GiftCertificate giftCertificate1, GiftCertificate giftCertificate2) {
        final String date1 = giftCertificate1.getCreateDate();
        final String date2 = giftCertificate2.getCreateDate();
        return ZonedDateTime.parse(date2).compareTo(ZonedDateTime.parse(date1));
    }

    private int compareByDateAsc(GiftCertificate giftCertificate1, GiftCertificate giftCertificate2) {
        final String date1 = giftCertificate1.getCreateDate();
        final String date2 = giftCertificate2.getCreateDate();
        return ZonedDateTime.parse(date1).compareTo(ZonedDateTime.parse(date2));
    }

    private Comparator<GiftCertificate> chooseComparator(String sortType) {
        if (sortType == null) {
            return this::compareByNameDesc;
        }

        switch (sortType) {
            case "date-asc": {
                return this::compareByDateAsc;
            }
            case "date-desc": {
                return this::compareByDateDesc;
            }
            case "name-asc": {
                return this::compareByNameAsc;
            }
            default: {
                return this::compareByNameDesc;
            }
        }
    }

}
