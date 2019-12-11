package com.zeng.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置类
 * @author zengzhanliang
 */
@ConfigurationProperties(prefix = "zeng.result")
@Data
public class ResultProperties {
    private boolean enabled;
}
