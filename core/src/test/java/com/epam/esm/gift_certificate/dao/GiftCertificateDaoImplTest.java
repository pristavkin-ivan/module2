package com.epam.esm.gift_certificate.dao;

import com.epam.esm.gift_certificate.config.TestContextConfig;
import com.epam.esm.gift_certificate.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.gift_certificate.entity.GiftCertificate;;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

@ContextConfiguration(classes = {TestContextConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class GiftCertificateDaoImplTest {

    private static GiftCertificate giftCertificate;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    GiftCertificateDaoImpl dao;

    /*private static DataSource dataSource;
    private static final String PATH_TO_PROP = "src/test/resources/db.properties";
    private static final String URL = "url";
    private static final String USER = "user";
    private static final String PASSWORD = "password";*/

    @BeforeAll
    public static void init() {
        final Properties properties = new Properties();

        giftCertificate = new GiftCertificate(0, "vasya", "asdasdasd fasf", 44.5, 10
                , "12.06.2021 17:07", "12.06.2021 15:07", new ArrayList<>());

        /*try {
            properties.load(new FileInputStream(PATH_TO_PROP));
            HikariConfig hikariConfig = new HikariConfig();

            hikariConfig.setJdbcUrl(properties.getProperty(URL));
            hikariConfig.setUsername(properties.getProperty(USER));
            hikariConfig.setPassword(properties.getProperty(PASSWORD));

            dataSource = new HikariDataSource(hikariConfig);

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Test
    public void selectAllTest() {

        Assertions.assertNotEquals(Collections.emptyList(), dao.getAll());
        System.out.println(dao.getAll());
    }

    /*@Test
    public void selectByIdTest() {
        Assertions.assertNotEquals(Optional.empty(), dao.get(4));
        System.out.println(dao.get(4));
    }

    @Test
    public void deleteByIdTest() {
        dao.delete(12);
    }

    @Test
    public void createTest() {
        dao.create(giftCertificate);
        System.out.println(dao.get(4));
    }

    @Test
    public void updateTest() {
        dao.update(10, giftCertificate);
    }*/

}
