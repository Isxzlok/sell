package com.xiongya.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-05-10 20:12
 */
@RestController
@Slf4j
@RequestMapping("/wx")
public class WeiXinController {

    @GetMapping("/auto")
    public void auto(@RequestParam("code")String code){
        log.info("进入auto方法");
        log.info("code={}", code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxab6370f90d80e64d&secret=ab372b55f6ea3472c24fd4cc9067dfd2&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        //发送一个get请求，返回的请求体将映射成一个对象


        String response = restTemplate.getForObject(url, String.class);
        System.out.println(response);
    }
}
