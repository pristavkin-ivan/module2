package com.epam.esm.gift_certificate.dao.api;

public interface SqlQueries {

    String SELECT_ALL_GIFT_CERTIFICATES = "select * from gift_certificate";
    String SELECT_GIFT_CERTIFICATE_BY_INDEX = "select * from gift_certificate where g_id = ?";
    String UPDATE_NAME = " update gift_certificate set g_name = ?, g_last_update_date = ? where g_id = ?";
    String DELETE_GIFT_CERTIFICATE = " delete from gift_certificate where g_id = ?";
    String INSERT_GIFT_CERTIFICATES = "insert into gift_certificate(g_name, g_description, g_price, g_duration" +
            ", g_create_date, g_last_update_date) values(?,?,?,?,?,?)";

    String SELECT_ALL_TAGS = "select * from tag";
    String SELECT_TAG_BY_INDEX = "select * from tag where t_id = ?";
    String DELETE_TAG = " delete from tag where t_id = ?";
    String INSERT_TAG = "insert into tag(t_name) values(?)";

}
