package com.xiongya.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-05-13 10:56
 */
@Component
@ConfigurationProperties(prefix = "wechat")
@Data
public class WeChatAccountConfig {

    private String mpAppId;

    private String mpAppSecret;



}
