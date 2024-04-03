package com.gpt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * openai用户信息配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "openai")
public class OpenAiConfig {

    // public String proxyHost;   // 代理地址

    // public Integer proxyPort;  // 代理端口

    private String keys;        // openai apikey

}

