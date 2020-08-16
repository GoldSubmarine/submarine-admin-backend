package com.htnova.common.dev.config;

import com.htnova.common.constant.GlobalConst;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.lang.NonNull;

public class DevValidatorImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(
        @NonNull AnnotationMetadata importingClassMetadata,
        @NonNull BeanDefinitionRegistry registry
    ) {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.addIncludeFilter(new AssignableTypeFilter(DevValidator.class));
        scanner.scan(GlobalConst.PACKAGE);
    }
}
