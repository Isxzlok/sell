package com.xiongya.sell.enums;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-24 09:15
 */
public enum ResultEnums {

    PRODUCT_NOT_EXIST(10,"商品不存在"),

    PRODUCT_STOCK_ERROR(11, "库存错误"),

    ORDER_NO_EXIST(12, "订单不存在"),

    ORDERDETAIL_NO_EXIST(13, "订单详情不存在"),

    ORDER_STATUS_ERROR(14, "不能取消该订单");



    private Integer code;

    private String message;

    ResultEnums(Integer code, String message) {

        this.code = code;
        this.message = message;
    }

    public Integer getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }


}
