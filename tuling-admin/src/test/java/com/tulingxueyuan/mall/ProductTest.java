package com.tulingxueyuan.mall;

import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductAttributeCategoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductTest {

    @Autowired
    PmsProductAttributeCategoryMapper pmsProductAttributeCategoryMapper;

    @Test
    public void test01(){
        System.out.println(pmsProductAttributeCategoryMapper.listWithAttr());
    }
}
