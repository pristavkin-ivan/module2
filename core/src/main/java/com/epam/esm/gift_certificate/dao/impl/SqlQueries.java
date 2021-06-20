package com.epam.esm.gift_certificate.dao.impl;

public class SqlQueries {

    /**
     * Gift_certificate queries
     */

    static String SELECT_ALL_GIFT_CERTIFICATES_BY_TAG = "select g_id, g_name, g_description, g_price, g_duration" +
            ", g_create_date, g_last_update_date from gift_certificate " +
            "join gifts_tags on g_id = gift_id " +
            "join tag on t_id = tag_id " +
            "where t_name = ? ";

    static String SELECT_GIFT_CERTIFICATE_BY_ID = "select g_id, g_name, g_description, g_price, g_duration" +
            ", g_create_date, g_last_update_date from gift_certificate where g_id = ?";

    static String DELETE_GIFT_CERTIFICATE = " delete from gift_certificate where g_id = ?";

    static String INSERT_GIFT_CERTIFICATES = "insert into gift_certificate(g_name, g_description, g_price, g_duration" +
            ", g_create_date, g_last_update_date) values(?,?,?,?,current_timestamp(),current_timestamp())";

    static String SELECT_LAST_INSERT_CERTIFICATE = "select g_id, g_name, g_description, g_price, g_duration" +
            ", g_create_date, g_last_update_date from gift_certificate where g_id = LAST_INSERT_ID()";

    static String UPDATE_GIFT_CERTIFICATE = "update gift_certificate set g_name = ?, g_description = ?, g_price = ?" +
            ", g_duration = ?, g_last_update_date = current_timestamp() where g_id = ?";

    /**
     * Gifts_tags queries
     */

    static String INSERT_ASSOCIATION = "insert into gifts_tags(gift_id, tag_id) values(?, ?)";

    static String DELETE_ASSOCIATION = "delete from gifts_tags where gift_id = ? and tag_id = ?";

    static String SELECT_ASSOCIATION_BY_TAG_ID = "select gift_id, tag_id " +
            "from gifts_tags where tag_id = ? and gift_id = ?";

    /**
     * tag queries
     */

    static String SELECT_ALL_TAGS = "select t_id, t_name from tag";

    static String SELECT_TAG_LIST = "select t_id, t_name from tag " +
            "join gifts_tags on t_id = tag_id " +
            "where gift_id = ?";

    static String SELECT_TAG_BY_INDEX = "select t_id, t_name from tag where t_id = ?";

    static String SELECT_TAG_BY_NAME = "select t_id, t_name from tag where t_name = ?";

    static String DELETE_TAG = " delete from tag where t_id = ?";

    static String INSERT_TAG = "insert into tag(t_name) values(?)";

}
