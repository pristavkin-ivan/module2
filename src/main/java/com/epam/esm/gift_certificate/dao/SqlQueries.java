package com.epam.esm.gift_certificate.dao;

public interface SqlQueries {

    String SELECT_ALL_GIFT_CERTIFICATES = "select * from gift_certificate";
    String SELECT_GIFT_CERTIFICATE_BY_INDEX = "select * from gift_certificate where g_id = ?";
    String UPDATE_NAME = " update gift_certificate set g_name = ? where g_id = ?";
    String DELETE_GIFT_CERTIFICATE = " delete from gift_certificate where g_id = ?";
    String INSERT_GIFT_CERTIFICATES = "insert into gift_certificate(g_name, g_description, g_price, g_duration" +
            ", g_create_date, g_last_update_date) values(?,?,?,?,?,?)";

    String ID_LABEL = "g_id";
    String NAME_LABEL = "g_name";
    String DURATION_LABEL = "g_duration";
    String DESCRIPTION_LABEL = "g_description";
    String PRICE_LABEL = "g_price";
    String CREATE_DATE_LABEL = "g_create_date";
    String LAST_UPDATE_DATE_LABEL = "g_last_update_date";
}
