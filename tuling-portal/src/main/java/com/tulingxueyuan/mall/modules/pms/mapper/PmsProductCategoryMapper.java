package com.tulingxueyuan.mall.modules.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tulingxueyuan.mall.dto.HomeMenusDTO;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;

import java.util.List;

/**
 * <p>
 * 产品分类 Mapper 接口
 * </p>
 *
 * @author XuShu
 * @since 2022-06-06
 */
public interface PmsProductCategoryMapper extends BaseMapper<PmsProductCategory> {

    List<HomeMenusDTO> getProductWithCategory();
}
