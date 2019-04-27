package com.xiongya.sell.service.Impl;

import com.xiongya.sell.Exception.SellException;
import com.xiongya.sell.dto.OrderDto;
import com.xiongya.sell.enums.ResultEnums;
import com.xiongya.sell.service.BuyerService;
import com.xiongya.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-27 09:58
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService{

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDto findOrderOne(String openid, String orderId) {
       return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDto cancelOrder(String openid, String orderId) {
        OrderDto orderDto = checkOrderOwner(openid, orderId);
        if (orderDto == null){
            log.error("【取消订单】查不到该订单，orderId={}", orderId);
            throw new SellException(ResultEnums.ORDER_NO_EXIST);
        }

        return orderService.cancel(orderDto);
    }

    private OrderDto checkOrderOwner(String openid, String orderId){
        OrderDto orderDto = orderService.findOne(orderId);
        if (orderDto == null){
            return null;
        }
        if (!orderDto.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("【查询订单】订单的openid不一致。 openid={}, orderDto={}", openid, orderDto);
            throw new SellException(ResultEnums.ORDER_OWNER_ERROR);
        }
        return orderDto;
    }
}
