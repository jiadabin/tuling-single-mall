package com.tulingxueyuan.mall.modules.pms.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="商品类型及属性关联信息传输对象", description="用于筛选属性以保存数据的初始化")
public class RelationAttrInfoDTO {
    private Long attributeCategoryId;
    private Long attributeId;
}
