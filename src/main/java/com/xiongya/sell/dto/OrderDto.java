package com.xiongya.sell.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xiongya.sell.dataObject.OrderDetail;
import com.xiongya.sell.enums.OrderStatusEnums;
import com.xiongya.sell.enums.PayStatusEnums;
import com.xiongya.sell.utils.Serializer.Date2LongSerializer;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-22 20:04
 */
@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) //这个注解主要是为了让为空的属性不传给前端
@JsonInclude(JsonInclude.Include.NON_NULL)//替代上面那个已经废弃掉的方法
public class OrderDto {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 买家名字
     */
    private String buyerName;

    /**
     * 买家名字
     */
    private String buyerPhone;

    /**
     * 买家地址
     */
    private String buyerAddress;

    /**
     * 买家微信openid
     */
    private String buyerOpenid;

    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态，默认为新下单，
     */
    private Integer orderStatus;

    /**
     * 支付状态
     */
    private Integer payStatus;

    /**
     * 创建时间
     * 这个注解主要是使用这个类将date类型转换成long类型
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;


    List<OrderDetail> orderDetailList;


}
