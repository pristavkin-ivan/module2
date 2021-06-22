package com.epam.esm.gift_certificate.dao.impl;

import com.epam.esm.gift_certificate.dao.api.GiftsTagsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;

@Component
public class GiftsTagsDaoImpl implements GiftsTagsDao {

    private final JdbcOperations jdbcOperations;

    @Autowired
    public GiftsTagsDaoImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public void createAssociation(int giftId, int tagId) {
        jdbcOperations.update(SqlQueries.INSERT_ASSOCIATION, giftId, tagId);
    }

    @Override
    public void deleteAllAssociationsById(int giftId, int tagId) {
        jdbcOperations.update(SqlQueries.DELETE_ASSOCIATION, giftId, tagId);
    }

}
