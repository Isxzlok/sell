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

    ORDER_STATUS_ERROR(14, "不能取消该订单"),

    ORDER_UPDATE_ERROR(15, "更新订单失败"),

    ORDER_DETAIL_IS_NULL(16, "该订单没有商品"),

    ORDER_PAY_STATUS_ERROR(17, "该订单已经支付"),

    PARAMS_ERROR(18, "参数错误"),

    CART_NOT_NULL(14, "购物车为空"),

    ORDER_OWNER_ERROR(15, "订单不属于该用户"),

    WECHAT_MP_ERROR(16, "微信网页授权错误")
    ;



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
