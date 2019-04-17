package com.xiongya.sell;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-17 14:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class loggerTest {

    private static Logger logger = LoggerFactory.getLogger(loggerTest.class);

    @Test
    public void fun1(){

        /**
         * 日志打印只输出info和error，因为系统默认的日志级别是info
         * 所以只能输出日志级别在info之上的和info
         * 日志级别 error > warn > info > debug > trace
         */
        logger.info("info........");
        logger.error("error........");
        logger.debug("debug.........");
    }

    //输出变量
    @Test
    public void fun2(){

        String name = "xiong";
        String password = "123456";

        //{}相当于一个占位符
        logger.info("name: {} , password: {}",name, password);

    }

}
