package com.tulingxueyuan.mall.modules.oms.service;

import com.tulingxueyuan.mall.dto.AddCartDTO;
import com.tulingxueyuan.mall.modules.oms.model.OmsCartItem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author XuShu
 * @since 2022-06-15
 */
public interface OmsCartItemService extends IService<OmsCartItem> {

    Boolean add(AddCartDTO addCartDTO);

    Integer getCarProdutSum();
}
