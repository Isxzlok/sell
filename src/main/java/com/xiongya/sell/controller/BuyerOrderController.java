package com.xiongya.sell.controller;

import com.xiongya.sell.Exception.SellException;
import com.xiongya.sell.convert.OrderForm2OrderDtoConverter;
import com.xiongya.sell.dto.OrderDto;
import com.xiongya.sell.enums.ResultEnums;
import com.xiongya.sell.form.OrderForm;
import com.xiongya.sell.form.OrderListForm;
import com.xiongya.sell.service.BuyerService;
import com.xiongya.sell.service.OrderService;
import com.xiongya.sell.utils.ResultVoUtils;
import com.xiongya.sell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-26 08:33
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @RequestMapping("/create")
    public ResultVo<Map<String, String >> create(@Valid OrderForm orderForm,
                                                 BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnums.PARAMS_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDto orderDto = OrderForm2OrderDtoConverter.convert(orderForm);

        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnums.CART_NOT_NULL);
        }
        OrderDto orderDto1 = orderService.create(orderDto);
        Map<String, String> map = new HashMap<>();
        map.put("orderId",orderDto1.getOrderId());
        ResultVo<Map<String, String>> resultVo = ResultVoUtils.success(map);

        return resultVo;

    }


    //订单列表
    @RequestMapping("/list")
    public ResultVo<List<OrderDto>> list(@Valid OrderListForm orderListForm,
                                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【订单列表】参数不正确，orderListForm={}", orderListForm);
            throw new SellException(ResultEnums.PARAMS_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        if (orderListForm.getPage() == null){
            orderListForm.setPage(0);
        }
        if (orderListForm.getSize() == null){
            orderListForm.setSize(10);
        }
        Pageable pageable = new PageRequest(orderListForm.getPage(), orderListForm.getSize());
        Page<OrderDto> page = orderService.findList(orderListForm.getOpenid(), pageable);
        List<OrderDto> orderList = page.getContent();

        return ResultVoUtils.success(orderList);

    }

    //订单详情
    @RequestMapping("/detail")
    public ResultVo<OrderDto> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)){
            log.error("【订单详情】参数不正确，openid={}, orderId={}",openid, orderId);
            throw new SellException(ResultEnums.PARAMS_ERROR);
        }

        OrderDto orderDto = buyerService.findOrderOne(openid, orderId);

        return ResultVoUtils.success(orderDto);
    }

    //取消订单
    @RequestMapping("/cancel")
    public ResultVo cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)){
            log.error("【订单详情】参数不正确，openid={}, orderId={}",openid, orderId);
            throw new SellException(ResultEnums.PARAMS_ERROR);
        }
        OrderDto orderDto = buyerService.cancelOrder(openid, orderId);

        return ResultVoUtils.success();
    }

}
