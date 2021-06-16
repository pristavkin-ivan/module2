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

import java.util.List;
import java.util.Optional;

//todo transaction manager
@Component
public final class GiftCertificateServiceImpl implements GiftCertificateService {

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
    public List<GiftCertificate> readAllGiftCertificates() {
        final List<GiftCertificate> giftCertificates = giftCertificateDao.getAll();

        giftCertificates.forEach((giftCertificate)
                -> giftCertificate.setTags(tagDao.getTagList(giftCertificate.getId())));

        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> readAllGiftCertificates(String tag) {
        final List<GiftCertificate> giftCertificates = giftCertificateDao.getAll(tag);

        giftCertificates.forEach((giftCertificate)
                -> giftCertificate.setTags(tagDao.getTagList(giftCertificate.getId())));

        return giftCertificates;
    }

    @Override
    public GiftCertificate readGiftCertificate(int id) {
        final Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.get(id);
        GiftCertificate giftCertificate = null;

        if (giftCertificateOptional.isPresent()) {
            giftCertificate = giftCertificateOptional.get();
            giftCertificate.setTags(tagDao.getTagList(id));
        }

        return giftCertificate;
    }

    @Override
    public void createGiftCertificate(GiftCertificate giftCertificate) {
        final String time = IsoDateUtil.getCurrentTimeInIsoFormat();
        final List<Tag> tags = giftCertificate.getTags();

        giftCertificate.setCreateDate(time);
        giftCertificate.setLastUpdateDate(time);
        giftCertificateDao.create(giftCertificate);

        if (tags != null) {
            populateTags(giftCertificateDao.getLastRow(), tags);
        }
    }

    @Override
    public void updateGiftCertificate(int id, GiftCertificate giftCertificate) {
        final String time = IsoDateUtil.getCurrentTimeInIsoFormat();

        giftCertificate.setLastUpdateDate(time);
        giftCertificateDao.update(id, giftCertificate);
    }

    @Override
    public void deleteGiftCertificate(int id) {
        giftCertificateDao.delete(id);
    }

    private void populateTags(GiftCertificate giftCertificate, List<Tag> tags) {
        for (Tag tag : tags) {
            Integer tag_id = tagDao.getTagByName(tag.getName()).orElse(tag).getId();

            if (!tagDao.getTagByName(tag.getName()).isPresent()) {

                tagDao.create(new Tag(tag_id, tag.getName()));
                tag_id = tagDao.getTagByName(tag.getName()).orElse(tag).getId();
            }

            if (!giftsTagsDao.isAssociationExists(tag_id)) {

                giftsTagsDao.createAssociation(giftCertificate.getId(), tag_id);
            }
        }
    }
}
