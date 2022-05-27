package com.tulingxueyuan.mall.modules.pms.model.dto;

import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProductCateDTO筛选商品类型数据传输对象", description = "用于商品类型--筛选属性下拉级联数据")
public class ProductCateWithChildrenDTO {

    private Long id;
    private String name;
    private List<ProductCateWithChildrenDTO> children;
}
