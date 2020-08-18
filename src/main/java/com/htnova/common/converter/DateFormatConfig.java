package com.htnova.common.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.htnova.common.constant.GlobalConst;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.exception.ServiceException;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

/** 全局 json 格式化 */
@JsonComponent
public class DateFormatConfig implements Converter<String, LocalDateTime> {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT_REGEXP = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
    private static final String SECOND_FORMAT_REGEXP = "^\\d{10}$";
    private static final String MILLI_FORMAT_REGEXP = "^\\d{13}$";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    // ===============================json格式化start（http body）=============================

    /** 日期格式化为字符串 */
    public static class DateJsonSerializer extends JsonSerializer<LocalDateTime> {

        @Override
        public void serialize(
            LocalDateTime localDateTime,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider
        )
            throws IOException {
            jsonGenerator.writeString(dateTimeFormatter.format(localDateTime));
        }
    }

    /** 解析日期字符串 */
    public static class DateJsonDeserializer extends JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
            return deserializer(jsonParser.getText());
        }
    }

    // ===============================json格式化end（http body）=============================

    // ===============================param格式化start（http url）=============================
    @Override
    public LocalDateTime convert(@Nullable String value) {
        return deserializer(value);
    }

    // ===============================param格式化end（http url）=============================

    /** 反序列化 */
    private static LocalDateTime deserializer(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        if (value.matches(DATE_FORMAT_REGEXP)) {
            return LocalDateTime.parse(value, dateTimeFormatter);
        }
        if (value.matches(SECOND_FORMAT_REGEXP)) {
            return LocalDateTime.ofInstant(
                Instant.ofEpochSecond(Long.parseLong(value)),
                ZoneId.of(GlobalConst.TIME_ZONE_ID)
            );
        }
        if (value.matches(MILLI_FORMAT_REGEXP)) {
            return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(Long.parseLong(value)),
                ZoneId.of(GlobalConst.TIME_ZONE_ID)
            );
        }
        throw new ServiceException(ResultStatus.FORMAT_ERROR);
    }
}
