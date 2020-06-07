package com.htnova.common.util;

import com.htnova.common.constant.GlobalConst;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    private DateUtil() {}

    public static LocalDateTime converter(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.of(GlobalConst.TIME_ZONE_ID));
    }
}
