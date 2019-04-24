package com.xiongya.sell.convert;

import com.xiongya.sell.dataObject.OrderMaster;
import com.xiongya.sell.dto.OrderDto;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-24 19:40
 */
public class OrderMaster2OrderDtoTOConverter {

    /**
     * 将orderMaster转换成OrderDto对象
     * @param orderMaster
     * @return
     */
    public static OrderDto convert(OrderMaster orderMaster){

        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDto);
        return orderDto;
    }

    public static List<OrderDto> convert(List<OrderMaster> orderMasterList){

        return orderMasterList.stream()
                .map(e -> convert(e))
                .collect(Collectors.toList());
    }

}
