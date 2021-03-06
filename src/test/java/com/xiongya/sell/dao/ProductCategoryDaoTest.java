package com.xiongya.sell.dao;

import com.xiongya.sell.dataObject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author xiongzhilong
 * @Email 2584496774@qq.com
 * @Date create by 2019-04-17 16:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void add(){

        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(2);
        productCategory.setCategoryName("主食");
        productCategory.setCategoryType(4);

        productCategoryDao.save(productCategory);

    }

    @Test
    public void fun(){
        List<Integer> list = new ArrayList<>();
        list.add(4);
        List<ProductCategory> list1 = productCategoryDao.findByCategoryTypeIn(list);
        System.out.println(list1);
    }

}