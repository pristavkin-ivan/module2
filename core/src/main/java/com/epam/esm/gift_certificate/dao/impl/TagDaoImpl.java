package com.epam.esm.gift_certificate.dao.impl;

import com.epam.esm.gift_certificate.dao.api.TagDao;
import com.epam.esm.gift_certificate.entity.Tag;
import com.epam.esm.gift_certificate.dao.api.SqlLabels;
import com.epam.esm.gift_certificate.dao.api.SqlQueries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Repository
public final class TagDaoImpl implements TagDao<Tag> {

    private final JdbcOperations jdbcOperations;

    private final static Logger LOGGER = LogManager.getLogger(TagDaoImpl.class);

    private final static String NO_SUCH_TAG = "No such tag";
    private final String SUCH_TAG_EXISTS = "Such tag is exists";

    @Autowired
    public TagDaoImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Tag> getAll() {
        return jdbcOperations.query(SqlQueries.SELECT_ALL_TAGS, this::mapTag);
    }

    @Override
    public List<Tag> getTagList(int id) {
        return jdbcOperations.query(SqlQueries.SELECT_TAG_LIST, this::mapTag, id);
    }

    @Override
    public Optional<Tag> get(int id) {
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject(
                    SqlQueries.SELECT_TAG_BY_INDEX
                    , this::mapTag
                    , id));
        } catch (IncorrectResultSizeDataAccessException exception) {
            //todo редирект на страницу ошибки с кастомным exception
            LOGGER.info(NO_SUCH_TAG);
        }
        return Optional.empty();
    }

    @Override
    public void delete(int id) {
        jdbcOperations.update(SqlQueries.DELETE_TAG, id);
    }

    @Override
    public void create(Tag tag) {
        try {
            jdbcOperations.update(SqlQueries.INSERT_TAG, tag.getName());
        } catch (DataAccessException exception) {
            LOGGER.info(SUCH_TAG_EXISTS);
        }
    }

    @Override
    public Optional<Tag> getTagByName(String name) {
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject(
                    SqlQueries.SELECT_TAG_BY_NAME
                    , this::mapTag
                    , name));
        } catch (IncorrectResultSizeDataAccessException exception) {
            //todo редирект на страницу ошибки с кастомным exception
            LOGGER.info(NO_SUCH_TAG);
        }
        return Optional.empty();
    }

    private Tag mapTag(ResultSet resultSet, int row) throws SQLException {
        return new Tag(
                resultSet.getInt(SqlLabels.T_ID)
                , resultSet.getString(SqlLabels.T_NAME));
    }

}
