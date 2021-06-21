package com.epam.esm.gift_certificate.service.impl;

public final class QueryParts {

    static String where = "where ";
    static String and = "and ";
    static String locateDescription = "locate(?,g_description) ";
    static String locateName = "locate(?,g_name) ";
    static String select = "select g_id, g_name, g_description, g_price, g_duration" +
            ", g_create_date, g_last_update_date from gift_certificate ";
    static String orderBy = "order by ";
}
