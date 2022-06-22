package com.tulingxueyuan.mall.modules.oms.mapper;

import com.tulingxueyuan.mall.dto.CartItemStockDTO;
import com.tulingxueyuan.mall.modules.oms.model.OmsCartItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 购物车表 Mapper 接口
 * </p>
 *
 * @author XuShu
 * @since 2022-06-15
 */
public interface OmsCartItemMapper extends BaseMapper<OmsCartItem> {

    List<CartItemStockDTO> getCartItemStock(Long id);
}
