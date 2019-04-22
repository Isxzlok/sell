package com.xiongya.sell.service;

import com.xiongya.sell.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-22 19:58
 */
public interface OrderService {

    /**
     * 创建订单
     */
    OrderDto create(OrderDto orderDto);

    /**
     * 查询单个订单
     */
    OrderDto findOne(String orderId);
    /**
     * 查询订单列表
     */
    Page<OrderDto> findList(String buyerOpenId, Pageable pageable);
    /**
     * 取消订单
     */
    OrderDto cancel(OrderDto orderDto);
    /**
     * 完结订单
     */
    OrderDto finish(OrderDto orderDto);
    /**
     * 支付订单
     */
    OrderDto paid(OrderDto orderDto);
}
