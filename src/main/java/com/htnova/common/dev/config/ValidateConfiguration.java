package com.htnova.common.dev.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnProperty(prefix = "dev.validator", name = "enable", havingValue = "true")
@Import(DevValidatorImportBeanDefinitionRegistrar.class)
public class ValidateConfiguration {}
