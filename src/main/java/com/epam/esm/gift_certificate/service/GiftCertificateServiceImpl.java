package com.epam.esm.gift_certificate.service;

import com.epam.esm.gift_certificate.dao.GiftCertificateDao;
import com.epam.esm.gift_certificate.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//todo transaction manager
@Component
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao) {
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
        giftCertificateDao.create(giftCertificate);
    }

    @Override
    public void updateGiftCertificate(int id, GiftCertificate giftCertificate) {
        giftCertificateDao.update(id, giftCertificate);
    }

    @Override
    public void deleteGiftCertificate(int id) {
        giftCertificateDao.delete(id);
    }
}
