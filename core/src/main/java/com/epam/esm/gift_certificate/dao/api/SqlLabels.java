package com.epam.esm.gift_certificate.dao.api;

public interface SqlLabels {

    /**
     * Gift_certificate table labels
     * */

    String G_ID = "g_id";
    String G_NAME = "g_name";
    String G_DURATION = "g_duration";
    String G_DESCRIPTION = "g_description";
    String G_PRICE = "g_price";
    String G_CREATE_DATE = "g_create_date";
    String G_LAST_UPDATE_DATE = "g_last_update_date";

    /**
     * Tag table labels
     */

    String T_ID = "t_id";
    String T_NAME = "t_name";
    String TAG_ID = "tag_id";

}
