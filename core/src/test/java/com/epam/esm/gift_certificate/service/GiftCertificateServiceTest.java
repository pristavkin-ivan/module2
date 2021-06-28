package com.epam.esm.gift_certificate.service;

import com.epam.esm.gift_certificate.config.TestContextConfig;
import com.epam.esm.gift_certificate.context.ParamContext;
import com.epam.esm.gift_certificate.dao.api.GiftCertificateDao;
import com.epam.esm.gift_certificate.dao.api.TagDao;
import com.epam.esm.gift_certificate.model.dto.GiftCertificateDto;
import com.epam.esm.gift_certificate.model.dto.TagDto;
import com.epam.esm.gift_certificate.model.entity.GiftCertificate;
import com.epam.esm.gift_certificate.service.api.GiftCertificateService;
import com.epam.esm.gift_certificate.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestContextConfig.class)
public class GiftCertificateServiceTest {

    static List<GiftCertificate> certificates;
    static List<TagDto> tags;

    @Autowired
    private GiftCertificateService giftCertificateService;

    @BeforeAll
    static void initLists() {
        certificates = Arrays.asList(new GiftCertificate(10, "cert10", "descr10"
                , 10.10, 115, new Timestamp(25055949), new Timestamp(260556547))
                , new GiftCertificate(9, "cert99", "descr9"
                , 9.9, 3000, new Timestamp(25055544), new Timestamp(260554545)));
        tags = Arrays.asList(new TagDto(1,"qwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq"));
    }

    @Test
    @SuppressWarnings("all")
    void readAllTest() {
        GiftCertificateDao certificateDaoMock = Mockito.mock(GiftCertificateDao.class);
        TagDao tagDaoMock = Mockito.mock(TagDao.class);
        GiftCertificateService service = new GiftCertificateServiceImpl(certificateDaoMock, tagDaoMock, null);

        Mockito.when(certificateDaoMock.getAll(Mockito.any())).thenReturn(certificates);
        Mockito.when(tagDaoMock.getTagsByGiftCertificateId(Mockito.anyInt())).thenReturn(Collections.emptyList());
        Assertions.assertNotNull(service.readAllGiftCertificates(new ParamContext(null, null)));
        System.out.println(service.readAllGiftCertificates(new ParamContext(null, null)));
    }

    @Test
    void createCertificateTest() {
        giftCertificateService.createGiftCertificate(new GiftCertificateDto(10, "cert10", "descr10"
                , 10.10, 115, "1970-01-01T09:57:35.949Z"
                , "1970-01-01T09:57:35.949Z", tags));
    }

}
