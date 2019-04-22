package com.xiongya.sell.dao;

import com.xiongya.sell.dataObject.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-22 19:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Test
    public void fun1(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerAddress("海南省海口市龙华区");
        orderMaster.setBuyerName("孙芳雅");
        orderMaster.setBuyerPhone("18889932540");
        orderMaster.setBuyerOpenid("22222222");
        orderMaster.setOrderAmount(new BigDecimal(35.8));
        orderMaster.setOrderId("2");
        orderMaster.setCreateTime(new Date());
        orderMasterDao.save(orderMaster);
    }

    @Test
    public void fun2(){
        PageRequest pageRequest = new PageRequest(0,3);
        Page<OrderMaster> page = orderMasterDao.findByBuyerOpenid("111111111", pageRequest);
        System.out.println(page.getTotalElements());

    }

}