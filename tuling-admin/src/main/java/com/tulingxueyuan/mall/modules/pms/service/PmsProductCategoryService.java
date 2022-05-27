package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductCateWithChildrenDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductCategoryDTO;

import java.util.List;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-19
 */
public interface PmsProductCategoryService extends IService<PmsProductCategory> {

    /**
     * 获取商品分类列表
     * @param parentId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page list(Long parentId, Integer pageNum, Integer pageSize);

    boolean updateNavStatus(List<Integer> ids, Integer navStatus);

    boolean updateShowStatus(List<Integer> ids, Integer showStatus);

    boolean deleteById(Long id);

    boolean customerSave(ProductCategoryDTO productCategoryDTO);

    boolean update(ProductCategoryDTO productCategoryDTO);

    List<ProductCateWithChildrenDTO> getWithChildren();
}
