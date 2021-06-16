package com.epam.esm.gift_certificate.service.impl;

import com.epam.esm.gift_certificate.dao.api.Dao;
import com.epam.esm.gift_certificate.dao.impl.GiftCertificateDao;
import com.epam.esm.gift_certificate.entity.GiftCertificate;
import com.epam.esm.gift_certificate.service.api.GiftCertificateService;
import com.epam.esm.gift_certificate.util.IsoDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//todo transaction manager
@Component
public final class GiftCertificateServiceImpl implements GiftCertificateService {

    private final Dao<GiftCertificate> giftCertificateDao;

    @Autowired
    public GiftCertificateServiceImpl(Dao<GiftCertificate> giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public List<GiftCertificate> readAllGiftCertificates() {

        return giftCertificateDao.getAll();
    }

    @Override
    public GiftCertificate readGiftCertificate(int id) {
        return giftCertificateDao.get(id).orElse(null);
    }

    @Override
    public void createGiftCertificate(GiftCertificate giftCertificate) {
        final String time = IsoDateUtil.getCurrentTimeInIsoFormat();

        giftCertificate.setCreateDate(time);
        giftCertificate.setLastUpdateDate(time);
        giftCertificateDao.create(giftCertificate);
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
}
