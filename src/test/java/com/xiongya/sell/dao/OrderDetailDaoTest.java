package com.xiongya.sell.dao;

import com.xiongya.sell.dataObject.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-22 19:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDaoTest {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Test
    public void fun1(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1");
        orderDetail.setOrderId("1");
        orderDetail.setProductName("腊八粥");
        orderDetail.setProductIcon("xxx.jpg");
        orderDetail.setProductPrice(new BigDecimal("2.5"));
        orderDetail.setProductQuantity(2);
        orderDetail.setProductId("3");

        orderDetailDao.save(orderDetail);
    }

    @Test
    public void fun2(){
        List<OrderDetail> list = orderDetailDao.findByOrderId("1");
        System.out.println(list.get(0));
    }

}