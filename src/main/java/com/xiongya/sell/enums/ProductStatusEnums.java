package com.xiongya.sell.enums;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-18 09:06
 *
 * 商品状态
 */
public enum ProductStatusEnums {

    UP(0,"在架"),
    DOWN(1, "已下架")
    ;

    private Integer code;
    private String message;

    ProductStatusEnums(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage(){
        return message;
    }
}
