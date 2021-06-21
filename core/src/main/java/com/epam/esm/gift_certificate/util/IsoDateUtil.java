package com.epam.esm.gift_certificate.util;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class IsoDateUtil {

    public static String getCurrentTimeInIsoFormat(Timestamp time) {
        return ZonedDateTime.of(time.toLocalDateTime(), ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
    }

}
