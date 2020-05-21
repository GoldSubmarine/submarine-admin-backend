package com.htnova.common.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.htnova.common.constant.GlobalConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 全局 json 格式化
 * @author charles
 */
@JsonComponent
public class DateFormatConfig {

    private DateFormatConfig() {}

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 日期格式化为字符串
     */
    public static class DateJsonSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(dateTimeFormatter.format(localDateTime));
        }
    }

    /**
     * 解析日期字符串
     */
    public static class DateJsonDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String value = jsonParser.getText();

            String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
            String timeStampFormat = "^\\d+$";

            if(StringUtils.isEmpty(value)) {
                return null;
            }
            if(value.matches(dateTimeFormat)) {
                return LocalDateTime.parse(value, dateTimeFormatter);
            }
            if(value.matches(timeStampFormat)) {
                return LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(value)), ZoneId.of(GlobalConst.TIME_ZONE_ID));
            }
            return null;
        }
    }
}
