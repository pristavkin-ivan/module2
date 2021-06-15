package com.epam.esm.gift_certificate.dao;

import com.epam.esm.gift_certificate.entity.GiftCertificate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDao implements Dao<GiftCertificate> {

    private final JdbcOperations jdbcOperations;

    private final static Logger LOGGER = LogManager.getLogger(GiftCertificateDao.class);

    @Autowired
    public GiftCertificateDao(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<GiftCertificate> getAll() {
        final List<GiftCertificate> giftCertificates = jdbcOperations
                .query(SqlQueries.SELECT_ALL_GIFT_CERTIFICATES, this::mapGiftCertificate);

        if (giftCertificates.isEmpty()) {
            return Collections.emptyList();
        }

        return giftCertificates;
    }

    @Override
    public Optional<GiftCertificate> get(int id) {
        return Optional.ofNullable(jdbcOperations.queryForObject(
                SqlQueries.SELECT_GIFT_CERTIFICATE_BY_INDEX
                , this::mapGiftCertificate
                , id));
    }

    @Override
    public void update(int id, GiftCertificate giftCertificate) {
       jdbcOperations.update(SqlQueries.UPDATE_NAME, giftCertificate.getName(), id);
    }

    @Override
    public void delete(int index) {
        jdbcOperations.update(SqlQueries.DELETE_GIFT_CERTIFICATE, index);
    }

    @Override
    public void create(GiftCertificate giftCertificate) {
        jdbcOperations.update(
                SqlQueries.INSERT_GIFT_CERTIFICATES
                , giftCertificate.getName()
                , giftCertificate.getDescription()
                , giftCertificate.getPrice()
                , giftCertificate.getDuration()
                , giftCertificate.getCreateDate()
                , giftCertificate.getLastUpdateDate());
    }

    private GiftCertificate mapGiftCertificate(ResultSet resultSet, int row) throws SQLException {
        return new GiftCertificate(
                resultSet.getInt(SqlQueries.ID_LABEL)
                , resultSet.getString(SqlQueries.NAME_LABEL)
                , resultSet.getString(SqlQueries.DESCRIPTION_LABEL)
                , resultSet.getDouble(SqlQueries.PRICE_LABEL)
                , resultSet.getInt(SqlQueries.DURATION_LABEL)
                , resultSet.getString(SqlQueries.CREATE_DATE_LABEL)
                , resultSet.getString(SqlQueries.LAST_UPDATE_DATE_LABEL));
    }


}
