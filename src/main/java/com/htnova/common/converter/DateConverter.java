package com.htnova.common.converter;

import com.htnova.common.constant.ResultStatus;
import org.apache.commons.lang3.StringUtils;
import com.htnova.common.exception.ServiceException;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义前端参数转 Date 对象的实现
 */
public class DateConverter implements Converter<String, Date> {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_STAMP_FORMAT = "^\\d+$";

    @Override
    public Date convert(String value) {

        if(StringUtils.isEmpty(value)) {
            return null;
        }

        value = value.trim();

        try {
            if (value.contains("-")) {
                SimpleDateFormat formatter;
                if (value.contains(":")) {
                    formatter = new SimpleDateFormat(DATE_FORMAT);
                } else {
                    formatter = new SimpleDateFormat(SHORT_DATE_FORMAT);
                }
                return formatter.parse(value);
            } else if (value.matches(TIME_STAMP_FORMAT)) {
                Long lDate = Long.parseLong(value);
                return new Date(lDate);
            }
        } catch (Exception e) {
            throw new ServiceException(ResultStatus.FORMAT_ERROR);
        }
        throw new ServiceException(ResultStatus.FORMAT_ERROR);
    }

}
