package com.epam.esm.gift_certificate.dao.impl;

import com.epam.esm.gift_certificate.dao.api.GiftsTagsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GiftsTagsDaoImpl implements GiftsTagsDao {

    private final JdbcOperations jdbcOperations;

    @Autowired
    public GiftsTagsDaoImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public void createAssociation(int gift_id, int tag_id) {
        jdbcOperations.update(SqlQueries.INSERT_ASSOCIATION, gift_id, tag_id);
    }

    @Override
    public void deleteAllAssociationsById(int gift_id, int tag_id) {
        jdbcOperations.update(SqlQueries.DELETE_ASSOCIATION, gift_id, tag_id);
    }

}
