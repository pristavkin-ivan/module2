package com.epam.esm.gift_certificate.dao.impl;

import com.epam.esm.gift_certificate.entity.Tag;
import com.epam.esm.gift_certificate.dao.api.Dao;
import com.epam.esm.gift_certificate.dao.api.SqlLabels;
import com.epam.esm.gift_certificate.dao.api.SqlQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public final class TagDao implements Dao<Tag> {

    private final JdbcOperations jdbcOperations;

    @Autowired
    public TagDao(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Tag> getAll() {
        return jdbcOperations.query(SqlQueries.SELECT_ALL_TAGS, this::mapTag);
    }

    @Override
    public Optional<Tag> get(int id) {
        return Optional.ofNullable(jdbcOperations.queryForObject(
                SqlQueries.SELECT_TAG_BY_INDEX
                , this::mapTag
                , id));
    }

    @Override
    public void delete(int id) {
        jdbcOperations.update(SqlQueries.DELETE_TAG, id);
    }

    @Override
    public void create(Tag tag) {
        jdbcOperations.update(SqlQueries.INSERT_TAG, tag.getName());
    }

    private Tag mapTag(ResultSet resultSet, int row) throws SQLException {
        return new Tag(
                resultSet.getInt(SqlLabels.T_ID)
                , resultSet.getString(SqlLabels.T_NAME));
    }

}
