package com.tulingxueyuan.mall.modules.pms.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProductAttrDTO筛选属性子数据传输对象", description = "用于商品分类--筛选属性下拉级联数据")
public class ProductAttrDTO {

    private Long id;
    private String name;
}
