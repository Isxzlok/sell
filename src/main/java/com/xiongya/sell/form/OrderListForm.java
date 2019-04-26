package com.xiongya.sell.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-26 14:05
 */
@Data
public class OrderListForm {

    /**
     * 微信openid
     */
    @NotEmpty(message = "openid不能为空")
    private String openid;

    /**
     * 当前页，从第0页开始
     */

    private Integer page;

    /**
     * 每页显示行数
     */

    private Integer size;

}
