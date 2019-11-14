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
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String shortDateFormat = "yyyy-MM-dd";
    private static final String timeStampFormat = "^\\d+$";

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
                    formatter = new SimpleDateFormat(dateFormat);
                } else {
                    formatter = new SimpleDateFormat(shortDateFormat);
                }
                return formatter.parse(value);
            } else if (value.matches(timeStampFormat)) {
                Long lDate = new Long(value);
                return new Date(lDate);
            }
        } catch (Exception e) {
            throw new ServiceException(ResultStatus.FORMAT_ERROR);
        }
        throw new ServiceException(ResultStatus.FORMAT_ERROR);
    }

}
