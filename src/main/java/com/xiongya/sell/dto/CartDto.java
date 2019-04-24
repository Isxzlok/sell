package com.xiongya.sell.dto;

import lombok.Data;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-24 11:12
 * 购物车
 */
@Data
public class CartDto {

    /**
     * 商品id
     */
    private String productId;

    /**
     * 数量
     */
    private Integer productQuantity;

    public CartDto(String productId, Integer productQuantity){

        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public CartDto(){

    }
}
