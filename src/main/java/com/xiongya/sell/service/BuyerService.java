package com.xiongya.sell.service;

import com.xiongya.sell.dto.OrderDto;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-26 16:38
 * 买家
 */
public interface BuyerService {

    //查询一个订单
    OrderDto findOrderOne(String openid, String orderId);

    //取消订单
    OrderDto cancelOrder(String openid, String orderId);
}
