package com.htnova.common.dev.validate;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.google.common.collect.Lists;
import com.htnova.common.dev.config.DevValidator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MybatisSqlInjectionValidator implements DevValidator {
    @Autowired
    private MybatisPlusProperties mybatisPlusProperties;

    @SneakyThrows
    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent applicationReadyEvent) {
        List<Resource> resources = new ArrayList<>();
        for (String mapperLocation : mybatisPlusProperties.getMapperLocations()) {
            List<Resource> resources1 = Lists.newArrayList(
                    new PathMatchingResourcePatternResolver().getResources(mapperLocation)
            );
            resources.addAll(resources1);
        }
        resources
                .stream()
                .collect(Collectors.toMap(Resource::getFilename, a -> a, (a, b) -> a))
                .values()
                .forEach(
                        resource -> {
                            try {
                                String xmlStr = FileUtils.readFileToString(resource.getFile(), Charset.defaultCharset());
                                if (StringUtils.contains(xmlStr, "${")) {
                                    log.warn(String.format("文件 [%s] 含有${}变量，可能引起sql注入", resource.getFilename()));
                                }
                            } catch (IOException e) {
                                log.error("文件读取失败", e);
                            }
                        }
                );
    }
}
