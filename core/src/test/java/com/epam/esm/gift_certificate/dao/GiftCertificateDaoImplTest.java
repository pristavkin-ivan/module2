package com.epam.esm.gift_certificate.dao;

import com.epam.esm.gift_certificate.config.TestContextConfig;
import com.epam.esm.gift_certificate.dao.api.GiftCertificateDao;
import com.epam.esm.gift_certificate.exception.NoSuchCertificateException;
import com.epam.esm.gift_certificate.model.entity.GiftCertificate;;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestContextConfig.class)
public class GiftCertificateDaoImplTest {

    private final GiftCertificateDao<GiftCertificate> giftCertificateDao;

    private static final GiftCertificate GIFT_CERTIFICATE = new GiftCertificate(10, "cert2", "gfgf"
            , 30.2, 15, null, null);

    @Autowired
    public GiftCertificateDaoImplTest(GiftCertificateDao<GiftCertificate> giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Test
    public void getByIdTest() {
        Assertions.assertDoesNotThrow(() -> giftCertificateDao.get(1));
    }

    @Test
    public void deleteByIdTest() throws NoSuchCertificateException {
        giftCertificateDao.delete(24);
        Assertions.assertThrows(NoSuchCertificateException.class, () -> giftCertificateDao.get(24));
    }

    @Test
    public void createTest() {
        Assertions.assertNotNull(giftCertificateDao.create(GIFT_CERTIFICATE));
    }

    @Test
    public void updateTest() {
        Assertions.assertDoesNotThrow(() -> giftCertificateDao.update(GIFT_CERTIFICATE));
    }

}
