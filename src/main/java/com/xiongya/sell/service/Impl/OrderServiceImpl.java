package com.xiongya.sell.service.Impl;

import com.xiongya.sell.Exception.SellException;
import com.xiongya.sell.convert.OrderMaster2OrderDtoTOConverter;
import com.xiongya.sell.dao.OrderDetailDao;
import com.xiongya.sell.dao.OrderMasterDao;
import com.xiongya.sell.dao.ProductInfoDao;
import com.xiongya.sell.dataObject.OrderDetail;
import com.xiongya.sell.dataObject.OrderMaster;
import com.xiongya.sell.dataObject.ProductInfo;
import com.xiongya.sell.dto.CartDto;
import com.xiongya.sell.dto.OrderDto;
import com.xiongya.sell.enums.OrderStatusEnums;
import com.xiongya.sell.enums.PayStatusEnums;
import com.xiongya.sell.enums.ResultEnums;
import com.xiongya.sell.service.OrderService;
import com.xiongya.sell.service.ProductInfoService;
import com.xiongya.sell.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-22 20:19
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductInfoDao productInfoDao;

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Autowired
    private ProductInfoService productInfoService;


    @Override




    @Transactional
    public OrderDto create(OrderDto orderDto) {

        String orderId = KeyUtils.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //查询商品
        List<OrderDetail> orderDetailList = orderDto.getOrderDetailList();

        for (OrderDetail orderDetail : orderDetailList){
            ProductInfo productInfo = new ProductInfo();
            productInfo.setProductId(orderDetail.getProductId());
            Example<ProductInfo> example = Example.of(productInfo);
            Optional<ProductInfo> option = productInfoDao.findOne(example);
            if (option.get() == null){
                throw new SellException(ResultEnums.PRODUCT_NOT_EXIST);
            }
            //计算订单总价
            orderAmount = option.get().getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //订单详情入库
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtils.getUniqueKey());
            BeanUtils.copyProperties(option.get(), orderDetail);
            orderDetailDao.save(orderDetail);
        }

        //写入orderMaster
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnums.WAIT.getCode());
        orderMasterDao.save(orderMaster);

        //减库存
        List<CartDto> cartDtoList = orderDto.getOrderDetailList()
                .stream()
                .map(e -> new CartDto(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDtoList);



        return null;
    }

    @Override
    public OrderDto findOne(String orderId) {

        OrderDto orderDto = new OrderDto();
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(orderId);
        Example<OrderMaster> example = Example.of(orderMaster);
        Optional<OrderMaster> option = orderMasterDao.findOne(example);
        OrderMaster orderMaster1 = option.get();
        if (orderMaster1 == null){
            throw new SellException(ResultEnums.ORDER_NO_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        if (orderDetailList.size() == 0){
            throw new SellException(ResultEnums.ORDERDETAIL_NO_EXIST);
        }
        BeanUtils.copyProperties(option.get(), orderDto);
        orderDto.setOrderDetailList(orderDetailList);

        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(String buyerOpenId, Pageable pageable) {

        Page<OrderMaster> page = orderMasterDao.findByBuyerOpenid(buyerOpenId, pageable);

        List<OrderDto> orderDtoList =
                OrderMaster2OrderDtoTOConverter.convert(page.getContent());
        Page<OrderDto> orderDtoPage =
                new PageImpl<OrderDto>(orderDtoList, pageable, page.getTotalElements());

        return orderDtoPage;
    }

    @Override
    public OrderDto cancel(OrderDto orderDto) {
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);

        //判断订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("【取消订单】订单状态不正确，orderId={},orderStatus={}", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_STATUS_ERROR);
        }
        //修改订单状态

        //返回库存

        //给用户退款
        return null;
    }

    @Override
    public OrderDto finish(OrderDto orderDto) {
        return null;
    }

    @Override
    public OrderDto paid(OrderDto orderDto) {
        return null;
    }
}
