package com.htnova.common.dev.validate;

import com.htnova.common.constant.GlobalConst;
import com.htnova.common.dev.config.DevValidator;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.lang.NonNull;

/**
 * 监测出时间为 Date 类型的字段，请使用 LocalDateTime
 */
@Slf4j
public class LocalDateTimeValidator implements DevValidator {

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(GlobalConst.PACKAGE).scan()) {
            ClassInfoList classInfos = scanResult.getAllClasses().filter(classInfo -> !classInfo.isInnerClass());
            classInfos.forEach(
                classInfo ->
                    classInfo
                        .getDeclaredFieldInfo()
                        .filter(
                            fieldInfo ->
                                "Date".equalsIgnoreCase(fieldInfo.getTypeDescriptor().toStringWithSimpleNames())
                        )
                        .filter(fieldInfo -> !fieldInfo.isStatic())
                        .forEach(
                            fieldInfo ->
                                log.warn(
                                    String.format(
                                        "类【%s】字段 [%s] 使用了 Date 类型",
                                        fieldInfo.getClassInfo().getSimpleName(),
                                        fieldInfo.getName()
                                    )
                                )
                        )
            );
        }
    }
}
