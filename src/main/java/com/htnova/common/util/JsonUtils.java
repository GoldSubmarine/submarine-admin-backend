package com.htnova.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class JsonUtils {

    private JsonUtils() {}

    @SneakyThrows
    public static String toJsonStr(Object object) {
        return getDefaultObjectMapper().writeValueAsString(object);
    }

    @SneakyThrows
    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        return getDefaultObjectMapper().readValue(jsonString, clazz);
    }

    @SneakyThrows
    public static <T> T fromJson(String jsonString, TypeReference<T> tTypeReference) {
        return getDefaultObjectMapper().readValue(jsonString, tTypeReference);
    }

    private static ObjectMapper objectMapper;

    private static ObjectMapper getDefaultObjectMapper() {
        if (Objects.isNull(objectMapper)) {
            synchronized (JsonUtils.class) {
                if (Objects.isNull(objectMapper)) {
                    objectMapper = SpringContextUtil.getBean(ObjectMapper.class);
                }
            }
        }
        return objectMapper;
    }
}
