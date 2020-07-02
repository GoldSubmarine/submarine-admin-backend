package com.htnova.common.util;

import com.htnova.common.constant.GlobalConst;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class DateUtil {

    private DateUtil() {}

    public static String format(LocalDateTime date, String formatString) {
        return DateTimeFormatter.ofPattern(formatString).format(date);
    }

    public static LocalDateTime converter(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.of(GlobalConst.TIME_ZONE_ID));
    }

    public static Date converter(LocalDateTime date) {
        if (Objects.isNull(date)) {
            return null;
        }
        return Date.from(date.toInstant(ZoneOffset.of(GlobalConst.TIME_ZONE_ID)));
    }
}
