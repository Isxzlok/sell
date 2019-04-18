package com.xiongya.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiongya.sell.dataObject.ProductInfo;
import lombok.Data;

import java.util.List;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-18 10:54
 *
 * 商品（包含类目）
 */
@Data
public class ProductVo {

    //@JsonProperty的注解的作用就是将该属性名称序列化为另一个名称，就是传给前台的json对象中的属性名称为name
    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVo> productInfoVoList;


}
