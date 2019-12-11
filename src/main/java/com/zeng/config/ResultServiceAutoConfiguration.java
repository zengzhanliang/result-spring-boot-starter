package com.zeng.config;

import com.zeng.model.ResultProperties;
import com.zeng.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动化配置类
 * @author zengzhanliang
 */
@Configuration
@EnableConfigurationProperties(ResultProperties.class)
@ConditionalOnClass(ResultService.class)
@ConditionalOnProperty(prefix = "zeng.result", value = "enabled", matchIfMissing = true, havingValue = "true")
public class ResultServiceAutoConfiguration {

    @Autowired
    private ResultProperties properties;

    @Bean
    @ConditionalOnMissingBean(ResultService.class)
    public ResultService resultService(){
        ResultService resultService = new ResultService(properties);
        return resultService;
    }
}
