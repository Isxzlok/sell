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


import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
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
        //设置这两个属性为空的原因是：因为这两个属性在创建时就已经有默认值了，后面的代码是根据订单Id查询的，如果这两个属性有默认值的话
        //findone那个方法就会根据订单id和这两个属性（订单状态，支付状态）去数据库匹配
        orderMaster.setPayStatus(null);
        orderMaster.setOrderStatus(null);

        Example<OrderMaster> example = Example.of(orderMaster);
        Optional<OrderMaster> option = orderMasterDao.findOne(example);
        if (!option.isPresent()){
            throw new SellException(ResultEnums.ORDER_NO_EXIST);
        }
        OrderMaster orderMaster1 = option.get();

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
    @Transactional
    public OrderDto cancel(OrderDto orderDto) {
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);

        //判断订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("【取消订单】订单状态不正确，orderId={},orderStatus={}", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
        orderDto.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
       OrderMaster updateOrderMaster = orderMasterDao.save(orderMaster);
       if (updateOrderMaster == null){
           log.error("【取消订单】更新失败，orderMaster={}", updateOrderMaster);
           throw new SellException(ResultEnums.ORDER_UPDATE_ERROR);
       }
        //返回库存
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情，orderDto={}", orderDto);
            throw new SellException(ResultEnums.ORDER_DETAIL_IS_NULL);
        }
        List<CartDto> cartDtoList = orderDto.getOrderDetailList().stream()
                .map(e -> new CartDto(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDtoList);

        //给用户退款
        if (orderDto.getPayStatus().equals(PayStatusEnums.SUCCESS.getCode())){
            //TODO
        }
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto finish(OrderDto orderDto) {

        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("【完结订单】订单状态不正确，orderId={},orderStatus={}", orderDto.getOrderId(), orderDto.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_STATUS_ERROR);
        }
        //修改状态
        orderDto.setOrderStatus(OrderStatusEnums.FINISH.getCode());
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster updateOrderMaster = orderMasterDao.save(orderMaster);
        if (updateOrderMaster == null){
            log.error("【完结订单】更新失败，orderMaster={}", updateOrderMaster);
            throw new SellException(ResultEnums.ORDER_UPDATE_ERROR);
        }
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto paid(OrderDto orderDto) {

        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("【完结订单】订单状态不正确，orderId={},orderStatus={}", orderDto.getOrderId(), orderDto.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDto.getPayStatus().equals(PayStatusEnums.WAIT.getCode())){
            log.error("【订单支付完成】订单支付状态不正确，orderDto={}", orderDto);
            throw new SellException(ResultEnums.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setPayStatus(PayStatusEnums.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster updateOrderMaster = orderMasterDao.save(orderMaster);
        if (updateOrderMaster == null){
            log.error("【支付订单】支付失败，orderMaster={}", updateOrderMaster);
            throw new SellException(ResultEnums.ORDER_PAY_STATUS_ERROR);
        }

        return orderDto;
    }
}
