package com.epam.esm.gift_certificate.dao.impl;

import com.epam.esm.gift_certificate.dao.api.GiftCertificateDao;
import com.epam.esm.gift_certificate.model.entity.GiftCertificate;

import com.epam.esm.gift_certificate.exception.NoSuchCertificateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao<GiftCertificate> {

    private final JdbcOperations jdbcOperations;

    private final static Logger LOGGER = LogManager.getLogger(GiftCertificateDaoImpl.class);

    private final static String NO_SUCH_CERTIFICATE = "No such gift-certificate! id: ";

    @Autowired
    public GiftCertificateDaoImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public List<GiftCertificate> getAll(String query, List<String> searchWords) {
        List<GiftCertificate> giftCertificates;

        if (searchWords == null || searchWords.isEmpty()) {
            giftCertificates = jdbcOperations.query(query, this::mapGiftCertificate);
        } else {
            giftCertificates = jdbcOperations.query(query, this::mapGiftCertificate, searchWords.toArray());
        }

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
    public Optional<GiftCertificate> get(int id) throws NoSuchCertificateException {
        GiftCertificate giftCertificate;

        try {
            giftCertificate = jdbcOperations.queryForObject(
                    SqlQueries.SELECT_GIFT_CERTIFICATE_BY_ID
                    , this::mapGiftCertificate
                    , id);
        } catch (IncorrectResultSizeDataAccessException exception) {
            LOGGER.info(NO_SUCH_CERTIFICATE + id);
            throw new NoSuchCertificateException(NO_SUCH_CERTIFICATE, id);
        }
        return Optional.ofNullable(giftCertificate);
    }

    @SuppressWarnings("all")
    @Override
    public void update(GiftCertificate giftCertificate) throws NoSuchCertificateException {
        GiftCertificate modifyingGiftCertificate = get(giftCertificate.getId()).get();

        updateLogic(modifyingGiftCertificate, giftCertificate);

        jdbcOperations.update(SqlQueries.UPDATE_GIFT_CERTIFICATE, modifyingGiftCertificate.getName()
                , modifyingGiftCertificate.getDescription(), modifyingGiftCertificate.getPrice()
                , modifyingGiftCertificate.getDuration(), modifyingGiftCertificate.getId());
    }

    @Override
    public void delete(int id) throws NoSuchCertificateException {
        try {
            jdbcOperations.update(SqlQueries.DELETE_GIFT_CERTIFICATE, id);
        } catch (DataAccessException exception) {
            LOGGER.info(NO_SUCH_CERTIFICATE + id);
            throw new NoSuchCertificateException(NO_SUCH_CERTIFICATE, id);
        }
    }

    @Override
    public Integer create(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcOperations.update(connection -> createPreparedStatement(connection, giftCertificate.getName()
                , giftCertificate.getDescription(), giftCertificate.getPrice(), giftCertificate.getDuration())
                , keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    private GiftCertificate mapGiftCertificate(ResultSet resultSet, int row) throws SQLException {
        return new GiftCertificate(
                resultSet.getInt(SqlLabels.CERTIFICATE_ID_COLUMN)
                , resultSet.getString(SqlLabels.CERTIFICATE_NAME_COLUMN)
                , resultSet.getString(SqlLabels.CERTIFICATE_DESCRIPTION_COLUMN)
                , resultSet.getDouble(SqlLabels.CERTIFICATE_PRICE_COLUMN)
                , resultSet.getInt(SqlLabels.CERTIFICATE_DURATION_COLUMN)
                , resultSet.getTimestamp(SqlLabels.CERTIFICATE_CREATE_DATE_COLUMN)
                , resultSet.getTimestamp(SqlLabels.CERTIFICATE_LAST_UPDATE_DATE_COLUMN));
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
    }

    private PreparedStatement createPreparedStatement(Connection connection, String name, String description
            , Double price, Integer duration) throws SQLException {

        PreparedStatement ps = connection.prepareStatement(SqlQueries.INSERT_GIFT_CERTIFICATE, new String[]{"id"});
        ps.setString(1, name);
        ps.setString(2, description);
        ps.setDouble(3, price);
        ps.setInt(4, duration);
        return ps;
    }

}
