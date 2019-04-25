package com.xiongya.sell.service.Impl;

import com.xiongya.sell.dataObject.OrderDetail;
import com.xiongya.sell.dataObject.OrderMaster;
import com.xiongya.sell.dto.CartDto;
import com.xiongya.sell.dto.OrderDto;
import com.xiongya.sell.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-24 14:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerAddress("浙江省杭州市江干区下沙头格月雅城");
        orderDto.setBuyerName("熊志龙");
        orderDto.setBuyerOpenid("33333333");
        orderDto.setBuyerPhone("189870397313");
        OrderDetail cartDto = new OrderDetail();
        cartDto.setProductId("1");
        cartDto.setProductQuantity(2);
        OrderDetail cartDto1 = new OrderDetail();
        cartDto1.setProductId("2");
        cartDto1.setProductQuantity(3);
        List<OrderDetail> list = new ArrayList<OrderDetail>();
        list.add(cartDto);
        list.add(cartDto1);
        orderDto.setOrderDetailList(list);
        orderService.create(orderDto);

    }

    @Test
    public void findOne() {

        OrderDto orderDto = orderService.findOne("1556103538451914465");
        System.out.println(orderDto);
    }

    @Test
    public void findList() {

        Pageable pageable = new PageRequest(0,2);
        Page<OrderDto> orderDtoPage =
                orderService.findList("33333333", pageable);
        System.out.println(orderDtoPage.getContent());
    }

    @Test
    public void cancel() {
        OrderDto orderDto = orderService.findOne("1556103538451914465");
        orderService.cancel(orderDto);
    }

    @Test
    public void finish() {
        OrderDto orderDto = orderService.findOne("1556103538451914465");
        orderService.finish(orderDto);
    }

    @Test
    public void paid() {
        OrderDto orderDto = orderService.findOne("1556103538451914465");
        orderService.paid(orderDto);
    }
}