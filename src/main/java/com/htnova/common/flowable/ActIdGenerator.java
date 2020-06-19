package com.htnova.common.flowable;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import javax.annotation.Resource;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * 文档地址：http://www.shareniu.com/flowable6.5_zh_document/bpm/index.html
 * https://jeesite.gitee.io/front/flowable/6.5.0/bpmn/index.html
 */
@Configuration
public class ActIdGenerator
        implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    @Resource private IdentifierGenerator identifierGenerator;

    @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
        springProcessEngineConfiguration.setIdGenerator(
                () -> String.valueOf(identifierGenerator.nextId(new Object()).longValue()));
    }
}
