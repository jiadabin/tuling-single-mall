package com.tulingxueyuan.mall.modules.pms.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProductAttributeCateDTO筛选属性数据传输对象", description = "用于商品分类--筛选属性下拉级联数据")
public class ProductAttributeCateDTO {

    private Long id;
    private String name;
    private List<ProductAttrDTO> productAttributeList;
}
