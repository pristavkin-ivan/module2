package com.epam.esm.gift_certificate.dao.api;

public interface SqlQueries {

    String SELECT_ALL_GIFT_CERTIFICATES = "select * from gift_certificate";
    String SELECT_ALL_GIFT_CERTIFICATES_BY_TAG = "select * from gift_certificate " +
            "join gifts_tags on g_id = gift_id " +
            "join tag on t_id = tag_id " +
            "where t_name = ? ";
    String SELECT_GIFT_CERTIFICATE_BY_ID = "select * from gift_certificate where g_id = ?";
    String UPDATE_NAME = " update gift_certificate set g_name = ?, g_last_update_date = ? where g_id = ?";
    String DELETE_GIFT_CERTIFICATE = " delete from gift_certificate where g_id = ?";
    String INSERT_GIFT_CERTIFICATES = "insert into gift_certificate(g_name, g_description, g_price, g_duration" +
            ", g_create_date, g_last_update_date) values(?,?,?,?,?,?)";
    String INSERT_ASSOCIATION = "insert into gifts_tags(gift_id, tag_id) values(?, ?)";
    String DELETE_ASSOCIATION = "delete from gifts_tags where tag_id = ?";
    String SELECT_ASSOCIATION_BY_TAG_ID = "select * from gifts_tags where tag_id = ?";

    String SELECT_ALL_TAGS = "select * from tag";
    String SELECT_TAG_LIST = "select * from tag " +
            "join gifts_tags on t_id = tag_id " +
            "where gift_id = ?";
    String SELECT_TAG_BY_INDEX = "select * from tag where t_id = ?";
    String SELECT_TAG_BY_NAME = "select * from tag where t_name = ?";
    String DELETE_TAG = " delete from tag where t_id = ?";
    String INSERT_TAG = "insert into tag(t_name) values(?)";
    String SELECT_LAST_INSERT_CERTIFICATE = "select * from gift_certificate where g_id = LAST_INSERT_ID()";

}
