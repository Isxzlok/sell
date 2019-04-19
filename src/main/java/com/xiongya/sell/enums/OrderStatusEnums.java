package com.xiongya.sell.enums;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-19 16:44
 */
public enum OrderStatusEnums {

    NEW(0, "新订单"),
    FINISH(1,"已完成订单"),
    CANCEL(2,"已取消")
    ;

    private Integer code;

    private String message;

    OrderStatusEnums(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }


}
