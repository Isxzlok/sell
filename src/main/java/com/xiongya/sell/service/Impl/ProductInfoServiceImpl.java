package com.xiongya.sell.service.Impl;

import com.xiongya.sell.dao.ProductInfoDao;
import com.xiongya.sell.dataObject.ProductInfo;
import com.xiongya.sell.enums.ProductStatusEnums;
import com.xiongya.sell.service.ProductInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-18 08:49
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Override
    public ProductInfo findOne(String productId) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(productId);
        Example<ProductInfo> example = Example.of(productInfo);
        Optional<ProductInfo> optional = productInfoDao.findOne(example);
        return optional.get();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        List<ProductInfo> list = productInfoDao.findByProductStatus(ProductStatusEnums.UP.getCode());
        return list;
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        Page<ProductInfo> pageList = productInfoDao.findAll(pageable);
        return pageList;
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        ProductInfo productInfo1 = productInfoDao.save(productInfo);
        return productInfo1;
    }
}
