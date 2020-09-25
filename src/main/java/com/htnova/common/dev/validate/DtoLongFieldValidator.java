package com.htnova.common.dev.validate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.htnova.common.constant.GlobalConst;
import com.htnova.common.dev.config.DevValidator;
import io.github.classgraph.*;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.lang.NonNull;

/**
 * DTO 定义 Long字段（由其是 Long用作ID的时候）
 * 必须指定 @JsonSerialize(using = ToStringSerializer.class)，否则序列化给前端后，前端科学计数法处理有问题
 *
 */
@Slf4j
public class DtoLongFieldValidator implements DevValidator {

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent applicationReadyEvent) {
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(GlobalConst.PACKAGE).scan()) {
            ClassInfoList dtoClassInfos = scanResult
                .getAllClasses()
                .filter(classInfo -> classInfo.getName().contains("Dto"))
                .filter(classInfo -> !classInfo.isInnerClass());
            dtoClassInfos.forEach(
                classInfo -> {
                    classInfo
                        .getDeclaredFieldInfo()
                        .filter(
                            fieldInfo ->
                                "Long".equalsIgnoreCase(fieldInfo.getTypeDescriptor().toStringWithSimpleNames())
                        )
                        .filter(fieldInfo -> !fieldInfo.isStatic())
                        .filter(
                            fieldInfo -> {
                                AnnotationInfo jsonSerialize = fieldInfo.getAnnotationInfo(
                                    JsonSerialize.class.getName()
                                );
                                if (Objects.nonNull(jsonSerialize)) {
                                    return !(ToStringSerializer.class.getName() + ".class").equals(
                                            jsonSerialize.getParameterValues().getValue("using").toString()
                                        );
                                }
                                return true;
                            }
                        )
                        .forEach(
                            fieldInfo ->
                                log.warn(
                                    String.format(
                                        "类【%s】字段 [%s] 没有添加 @JsonSerialize(using = ToStringSerializer.class) 注解",
                                        fieldInfo.getClassInfo().getSimpleName(),
                                        fieldInfo.getName()
                                    )
                                )
                        );
                }
            );
        }
    }
}
