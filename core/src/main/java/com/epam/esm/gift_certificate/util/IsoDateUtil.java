package com.epam.esm.gift_certificate.util;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class IsoDateUtil {

    private static final int HOUR_OFFSET = 3;

    public static String getCurrentTimeInIsoFormat() {
        return ZonedDateTime.now(ZoneOffset.UTC).plusHours(HOUR_OFFSET).format(DateTimeFormatter.ISO_INSTANT);
    }

}
