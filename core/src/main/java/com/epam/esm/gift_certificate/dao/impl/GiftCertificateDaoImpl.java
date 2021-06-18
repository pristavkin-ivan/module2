package com.epam.esm.gift_certificate.dao.impl;

import com.epam.esm.gift_certificate.dao.api.GiftCertificateDao;
import com.epam.esm.gift_certificate.entity.GiftCertificate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//todo бросать кастомные exception, обработать в Exception handler controller
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao<GiftCertificate> {

    private final JdbcOperations jdbcOperations;

    private final static Logger LOGGER = LogManager.getLogger(GiftCertificateDaoImpl.class);

    private final static String NO_SUCH_CERTIFICATE = "No such gift-certificate";

    @Autowired
    public GiftCertificateDaoImpl(JdbcOperations jdbcOperations) {
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
    public List<GiftCertificate> getAll(String tag) {
        final List<GiftCertificate> giftCertificates = jdbcOperations
                .query(SqlQueries.SELECT_ALL_GIFT_CERTIFICATES_BY_TAG, this::mapGiftCertificate, tag);

        if (giftCertificates.isEmpty()) {
            return Collections.emptyList();
        }

        return giftCertificates;
    }

    @Override
    public Optional<GiftCertificate> getLastRow() {
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject(
                    SqlQueries.SELECT_LAST_INSERT_CERTIFICATE
                    , this::mapGiftCertificate));
        } catch (EmptyResultDataAccessException exception) {
            LOGGER.info(exception);
        }
        return Optional.empty();
    }

    @Override
    public Optional<GiftCertificate> get(int id) {
        GiftCertificate giftCertificate = null;

        try {
            giftCertificate = jdbcOperations.queryForObject(
                    SqlQueries.SELECT_GIFT_CERTIFICATE_BY_ID
                    , this::mapGiftCertificate
                    , id);
        } catch (IncorrectResultSizeDataAccessException exception) {
            LOGGER.info(NO_SUCH_CERTIFICATE);
        }
        return Optional.ofNullable(giftCertificate);
    }

    @SuppressWarnings("all")
    @Override
    public void update(GiftCertificate giftCertificate) {
        GiftCertificate modifyingGiftCertificate = get(giftCertificate.getId()).get();

        updateLogic(modifyingGiftCertificate, giftCertificate);

        jdbcOperations.update(SqlQueries.UPDATE_GIFT_CERTIFICATE, modifyingGiftCertificate.getName()
                , modifyingGiftCertificate.getDescription(), modifyingGiftCertificate.getPrice()
                , modifyingGiftCertificate.getDuration(), modifyingGiftCertificate.getLastUpdateDate()
                , modifyingGiftCertificate.getId());
    }

    @Override
    public void delete(int id) {
        jdbcOperations.update(SqlQueries.DELETE_GIFT_CERTIFICATE, id);
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
                resultSet.getInt(SqlLabels.G_ID)
                , resultSet.getString(SqlLabels.G_NAME)
                , resultSet.getString(SqlLabels.G_DESCRIPTION)
                , resultSet.getDouble(SqlLabels.G_PRICE)
                , resultSet.getInt(SqlLabels.G_DURATION)
                , resultSet.getString(SqlLabels.G_CREATE_DATE)
                , resultSet.getString(SqlLabels.G_LAST_UPDATE_DATE)
                , new ArrayList<>());
    }

    private void updateLogic(GiftCertificate modifyingGiftCertificate, GiftCertificate giftCertificate) {
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

        modifyingGiftCertificate.setLastUpdateDate(giftCertificate.getLastUpdateDate());
    }

}
