package com.xiongya.sell.controller;

import com.xiongya.sell.dataObject.ProductCategory;
import com.xiongya.sell.dataObject.ProductInfo;
import com.xiongya.sell.service.ProductCategoryService;
import com.xiongya.sell.service.ProductInfoService;
import com.xiongya.sell.utils.ResultVoUtils;
import com.xiongya.sell.vo.ProductInfoVo;
import com.xiongya.sell.vo.ProductVo;
import com.xiongya.sell.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-18 10:20
 * 买家商品
 */
@RestController
@RequestMapping("buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;


    @GetMapping("/list")
    public ResultVo list(){
        //查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        //查询类目
        List<Integer> categoryTypeList =
                productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());

        List<ProductCategory> productCategoryList =
                productCategoryService.findByCategoryTypeIn(categoryTypeList);


            List<ProductVo> productVoList = new ArrayList<>();
            for (ProductCategory productCategory : productCategoryList) {
                ProductVo productVo = new ProductVo();
                productVo.setCategoryName(productCategory.getCategoryName());
                productVo.setCategoryType(productCategory.getCategoryType());

                List<ProductInfoVo> productInfoVoList = new ArrayList<>();
                for (ProductInfo productInfo : productInfoList) {
                    if (productCategory.getCategoryType().equals(productInfo.getCategoryType())) {
                        ProductInfoVo productInfoVo = new ProductInfoVo();
                        BeanUtils.copyProperties(productInfo, productInfoVo);
                        productInfoVoList.add(productInfoVo);
                    }
                }
                productVo.setProductInfoVoList(productInfoVoList);
                productVoList.add(productVo);
            }



        return ResultVoUtils.success(productVoList);

    }

}
