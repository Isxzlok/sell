package com.xiongya.sell.config;


import me.chanjar.weixin.mp.api.*;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-05-13 10:45
 */
@Component
public class WeChatMpConfig {

    @Autowired
    private WeChatAccountConfig weChatAccountConfig;

    @Bean
    public WxMpService wxMpService(){

       WxMpService wxMpService = new WxMpServiceImpl();
       wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
       return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId(weChatAccountConfig.getMpAppId());
        wxMpConfigStorage.setSecret(weChatAccountConfig.getMpAppSecret());
        return wxMpConfigStorage;
    }
}
