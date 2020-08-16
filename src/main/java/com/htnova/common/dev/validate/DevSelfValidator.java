package com.htnova.common.dev.validate;

import com.htnova.common.constant.GlobalConst;
import com.htnova.common.dev.config.DevValidator;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
public class DevSelfValidator implements DevValidator {

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent applicationReadyEvent) {
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(GlobalConst.PACKAGE).scan()) {
            ClassInfoList devValidators = scanResult.getClassesImplementing(DevValidator.class.getName());
            devValidators.forEach(
                classInfo -> {
                    if (classInfo.hasAnnotation(Component.class.getName())) {
                        log.error(String.format("【%s】不应该有 @Component 注解", classInfo.getName()));
                    }
                }
            );
        }
    }
}
