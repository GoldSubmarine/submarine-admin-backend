package com.htnova.common.dev.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

public interface DevValidator extends ApplicationListener<ApplicationReadyEvent> {}
