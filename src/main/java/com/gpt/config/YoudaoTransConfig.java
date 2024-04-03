package com.gpt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 有道翻译用户信息配置
 **/


@Data   // 从yml文件中读取配置信息，setter数据，提供getter方法进行调用
@Component
@ConfigurationProperties(prefix = "youdao")
public class YoudaoTransConfig {

    private String appKey;

    private String appSecret;

    private String from;  // from 翻译来源

    private String to;  // to 翻译目标

}
