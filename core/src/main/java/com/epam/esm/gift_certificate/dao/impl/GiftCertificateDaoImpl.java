package com.epam.esm.gift_certificate.dao.impl;

import com.epam.esm.gift_certificate.dao.api.GiftCertificateDao;
import com.epam.esm.gift_certificate.model.entity.GiftCertificate;

import com.epam.esm.gift_certificate.exception.NoSuchCertificateException;
import com.epam.esm.gift_certificate.context.ParamContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao<GiftCertificate> {

    private final JdbcOperations jdbcOperations;

    private final static Logger LOGGER = LogManager.getLogger(GiftCertificateDaoImpl.class);

    private final static String NO_SUCH_CERTIFICATE = "No such gift-certificate! id: ";

    private final static String NAME_FIELD = "name";

    private final static String DESCRIPTION_FIELD = "description";

    private final static String TAG_FIELD = "tag";

    @Autowired
    public GiftCertificateDaoImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<GiftCertificate> getAll(ParamContext paramContext) {
        final String query = resolveQuery(paramContext);

        return getCertificates(paramContext, query);
    }

    @Override
    public GiftCertificate get(int id) {
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
        return giftCertificate;
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        jdbcOperations.update(SqlQueries.UPDATE_GIFT_CERTIFICATE, giftCertificate.getName()
                , giftCertificate.getDescription(), giftCertificate.getPrice()
                , giftCertificate.getDuration(), giftCertificate.getId());
    }

    @Override
    public void delete(int id) {
        jdbcOperations.update(SqlQueries.DELETE_GIFT_CERTIFICATE, id);
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

    private PreparedStatement createPreparedStatement(Connection connection, String name, String description
            , Double price, Integer duration) throws SQLException {

        PreparedStatement ps = connection.prepareStatement(SqlQueries.INSERT_GIFT_CERTIFICATE, new String[]{"id"});
        ps.setString(1, name);
        ps.setString(2, description);
        ps.setDouble(3, price);
        ps.setInt(4, duration);
        return ps;
    }

    private List<GiftCertificate> getCertificates(ParamContext paramContext, String query) {
        ArrayList<String> searchWords = null;

        List<GiftCertificate> giftCertificates;

        if (!paramContext.getSearchMap().values().isEmpty()) {
            searchWords = new ArrayList<>(paramContext.getSearchMap().values());
        }

        giftCertificates = chooseQuery(query, searchWords);

        return giftCertificates;
    }

    private List<GiftCertificate> chooseQuery(String query, ArrayList<String> searchWords) {
        List<GiftCertificate> giftCertificates;
        if (searchWords == null || searchWords.isEmpty()) {
            giftCertificates = jdbcOperations.query(query, this::mapGiftCertificate);
        } else {
            giftCertificates = jdbcOperations.query(query, this::mapGiftCertificate, searchWords.toArray());
        }
        return giftCertificates;
    }

    private String resolveQuery(ParamContext paramContext) {
        StringBuffer query = new StringBuffer(QueryParts.select);

        paramContext.setSortTypes(paramContext.getSortTypes().stream().map(this::replaceStrings)
                .collect(Collectors.toList()));
        searchLogic(paramContext, query);
        sortLogic(paramContext, QueryParts.orderBy, query);

        return query.toString();
    }

    private void sortLogic(ParamContext paramContext, String orderBy, StringBuffer query) {
        if (!paramContext.getSortTypes().isEmpty()) {
            query.append(orderBy).append(paramContext.getSortTypes().get(0));
            paramContext.getSortTypes().stream().skip(1).forEach((sortType) -> query.append(", ").append(sortType));
        }
    }

    private void searchLogic(ParamContext paramContext, StringBuffer query) {

        if (!paramContext.getSearchMap().isEmpty()) {
            if (paramContext.getSearchMap().get(TAG_FIELD) != null) {
                query.append(QueryParts.join);
            }
            query.append(QueryParts.where);

            if (paramContext.getSearchMap().get(NAME_FIELD) != null) {
                query.append(QueryParts.locateName);
            }

            if (paramContext.getSearchMap().get(DESCRIPTION_FIELD) != null) {
                if (paramContext.getSearchMap().size() > 1) {
                    query.append(QueryParts.and);
                }
                query.append(QueryParts.locateDescription);
            }

            if (paramContext.getSearchMap().get(TAG_FIELD) != null) {
                if (paramContext.getSearchMap().size() > 1) {
                    query.append(QueryParts.and);
                }
                query.append(QueryParts.locateTag);
            }

        }
    }

    private String replaceStrings(String sortType) {
        String replaceName;

        replaceName = sortType.replace("name", "g_name");
        return replaceName.replace("date", "g_create_date");
    }

}
