package com.tulingxueyuan.mall.modules.oms.service;

import com.tulingxueyuan.mall.dto.AddCartDTO;
import com.tulingxueyuan.mall.dto.CartItemStockDTO;
import com.tulingxueyuan.mall.modules.oms.model.OmsCartItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    List<CartItemStockDTO> getList();

    Boolean updateQuantity(Long id, Integer quantity);

    Boolean delete(Long ids);
}
