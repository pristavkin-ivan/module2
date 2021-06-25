package com.epam.esm.gift_certificate.dao.impl;

import com.epam.esm.gift_certificate.dao.api.TagDao;
import com.epam.esm.gift_certificate.exception.NoSuchTagException;
import com.epam.esm.gift_certificate.exception.TagCreationException;
import com.epam.esm.gift_certificate.model.entity.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public final class TagDaoImpl implements TagDao<Tag> {

    private final JdbcOperations jdbcOperations;

    private final static Logger LOGGER = LogManager.getLogger(TagDaoImpl.class);

    private final static String NO_SUCH_TAG_NAME = "No such tag! name: ";

    private final static String NO_SUCH_TAG_ID = "No such tag! id: ";

    private final static String SUCH_TAG_EXISTS = "Such tag is already exists! name: ";

    @Autowired
    public TagDaoImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Tag> getAll() {
        return jdbcOperations.query(SqlQueries.SELECT_ALL_TAGS, this::mapTag);
    }

    @Override
    public List<Tag> getTagsByGiftCertificateId(int giftCertificateId) {
        return jdbcOperations.query(SqlQueries.SELECT_TAG_LIST, this::mapTag, giftCertificateId);
    }

    @Override
    public Tag get(int id) {
        try {
            return jdbcOperations.queryForObject(
                    SqlQueries.SELECT_TAG_BY_INDEX
                    , this::mapTag
                    , id);
        } catch (IncorrectResultSizeDataAccessException exception) {
            LOGGER.info(NO_SUCH_TAG_ID + id);
            throw new NoSuchTagException(NO_SUCH_TAG_ID, id);
        }
    }

    @Override
    public void delete(int id) throws NoSuchTagException {
        try {
            jdbcOperations.update(SqlQueries.DELETE_TAG, id);
        } catch (DataAccessException exception) {
            LOGGER.info(NO_SUCH_TAG_ID + id);
            throw new NoSuchTagException(NO_SUCH_TAG_ID, id);
        }
    }

    @Override
    public Tag create(Tag tag) {
        try {
            jdbcOperations.update(SqlQueries.INSERT_TAG, tag.getName());
        } catch (DataAccessException exception) {
            LOGGER.info(SUCH_TAG_EXISTS + tag.getName());
            throw new TagCreationException(SUCH_TAG_EXISTS, tag.getName());
        }
        return getTagByName(tag.getName());
    }

    @Override
    public Tag getTagByName(String tagName) {
        try {
            return jdbcOperations.queryForObject(
                    SqlQueries.SELECT_TAG_BY_NAME
                    , this::mapTag
                    , tagName);
        } catch (IncorrectResultSizeDataAccessException exception) {
            LOGGER.info(NO_SUCH_TAG_NAME + tagName);
            throw new NoSuchTagException(NO_SUCH_TAG_NAME, tagName);
        }
    }

    private Tag mapTag(ResultSet resultSet, int row) throws SQLException {
        return new Tag(
                resultSet.getInt(SqlLabels.TAG_ID_COLUMN)
                , resultSet.getString(SqlLabels.TAG_NAME_COLUMN));
    }

}
