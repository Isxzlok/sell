package com.xiongya.sell.convert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiongya.sell.Exception.SellException;
import com.xiongya.sell.dataObject.OrderDetail;
import com.xiongya.sell.dto.OrderDto;
import com.xiongya.sell.enums.ResultEnums;
import com.xiongya.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-26 09:12
 */
@Slf4j
public class OrderForm2OrderDtoConverter {

    /**
     * 将orderForm转换成OrderDto
     * @return
     */
    public static OrderDto convert(OrderForm orderForm){

        Gson gson = new Gson();

        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try{
            orderDetailList = gson.fromJson(orderForm.getItems(),
                new TypeToken<List<OrderDetail>>(){}.getType());
        }catch(Exception e){

            log.error("【对象转换】错误，String={}", orderForm.getItems());
            throw new SellException(ResultEnums.PARAMS_ERROR);
        }
       orderDto.setOrderDetailList(orderDetailList);

        return orderDto;


    }

}
