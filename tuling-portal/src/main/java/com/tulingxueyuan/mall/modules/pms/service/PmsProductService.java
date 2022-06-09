package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.ProductDetailDTO;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author XuShu
 * @since 2022-06-06
 */
public interface PmsProductService extends IService<PmsProduct> {

    ProductDetailDTO getProductDetail(Long id);
}
