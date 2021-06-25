package com.epam.esm.gift_certificate.dao.impl;

public final class QueryParts {

    static String where = "where ";
    static String and = "and ";
    static String locateDescription = "locate(?,g_description) ";
    static String locateName = "locate(?,g_name) ";
    static String locateTag = "locate(?,t_name) ";
    static String select = "select g_id, g_name, g_description, g_price, g_duration" +
            ", g_create_date, g_last_update_date from gift_certificate ";
    static String join = "join gifts_tags on g_id = gift_id " +
            "join tag on tag_id = t_id ";
    static String orderBy = "order by ";
}
