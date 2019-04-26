package com.xiongya.sell.Exception;

import com.xiongya.sell.enums.ResultEnums;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-24 09:14
 */
public class SellException extends RuntimeException{


    private Integer code;

    public SellException(ResultEnums resultEnums) {

        super(resultEnums.getMessage());
        this.code = resultEnums.getCode();
    }

    public  SellException(Integer code, String message){
        super(message);
        this.code = code;
    }
}
