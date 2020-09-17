package com.htnova.common.dev.validate;

import com.htnova.common.constant.GlobalConst;
import com.htnova.common.dev.config.DevValidator;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.lang.NonNull;

@Slf4j
public class SuperBuilderValidator implements DevValidator {

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent applicationReadyEvent) {
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(GlobalConst.PACKAGE).scan()) {
            scanResult
                .getAllClasses()
                .filter(classInfo -> !classInfo.isInnerClass())
                .filter(classInfo -> !classInfo.getSuperclasses().isEmpty())
                .forEach(
                    classInfo -> {
                        boolean isAnyRelatedClassHasSuperBuilder = Stream
                            .concat(classInfo.getSuperclasses().stream(), classInfo.getSubclasses().stream())
                            .anyMatch(this::hasSuperBuilderAnnotation);
                        if (isAnyRelatedClassHasSuperBuilder && !hasSuperBuilderAnnotation(classInfo)) {
                            log.warn(String.format("类【%s】需要添加 SuperBuilder 注解", classInfo.getSimpleName()));
                        }
                    }
                );
        }
    }

    private boolean hasSuperBuilderAnnotation(ClassInfo relatedClassInfo) {
        return relatedClassInfo
            .getInnerClasses()
            .stream()
            .anyMatch(
                innerClass -> {
                    // 该内部类为抽象类，且类名以 Builder 结尾，则说明改类拥有 SuperBuilder 注解
                    return innerClass.isAbstract() && StringUtils.endsWith(innerClass.getSimpleName(), "Builder");
                }
            );
    }
}
