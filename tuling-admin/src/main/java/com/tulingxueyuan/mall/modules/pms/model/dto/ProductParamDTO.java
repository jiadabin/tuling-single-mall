package com.tulingxueyuan.mall.modules.pms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {
 *     keyword: null,
 *     pageNum: 1,
 *     pageSize: 5,
 *     publishStatus: null,
 *     verifyStatus: null,
 *     productSn: null,
 *     productCategoryId: null,
 *     brandId: null
 *   }
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "商品列表查询参数DTO", description = "用于商品列表查询参数接收")
public class ProductParamDTO {

    private String keyword;
    private Integer pageNum;
    private Integer pageSize;
    private Integer publishStatus;
    private Integer verifyStatus;
    private String productSn;
    private Long productCategoryId;
    private Long brandId;
}
