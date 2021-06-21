package com.epam.esm.gift_certificate.dao;

import com.epam.esm.gift_certificate.model.entity.GiftCertificate;;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.util.ArrayList;


public class GiftCertificateDaoImplTest {

    private static GiftCertificate giftCertificate;

    private ApplicationContext applicationContext;

    private static DataSource dataSource;
    private static final String PATH_TO_PROP = "src/test/resources/db.properties";
    private static final String URL = "jdbc:mysql://localhost:3306/giftcertificates?serverTimezone=Europe/Moscow";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678L";

    @BeforeAll
    public static void init() {

        giftCertificate = new GiftCertificate(2, "cert2", "gfgf", 30.2, 15
                , null, null, new ArrayList<>());

            HikariConfig hikariConfig = new HikariConfig();

            hikariConfig.setJdbcUrl(URL);
            hikariConfig.setUsername(USER);
            hikariConfig.setPassword(PASSWORD);

            dataSource = new HikariDataSource(hikariConfig);

    }

    @Test
    public void selectAllTest() {
        /*GiftCertificateDaoImpl dao = new GiftCertificateDaoImpl(new JdbcTemplate(dataSource));
        dao.create(giftCertificate);*/
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
