package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductCategoryMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategoryAttributeRelation;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductCateWithChildrenDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductCategoryDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryAttributeRelationService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-19
 */
@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements PmsProductCategoryService {

    @Autowired
    private PmsProductCategoryMapper pmsProductCategoryMapper;

    @Autowired
    private PmsProductCategoryAttributeRelationService relationService;

    @Override
    public Page list(Long parentId, Integer pageNum, Integer pageSize) {

        Page page = new Page(pageNum, pageSize);
        QueryWrapper<PmsProductCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PmsProductCategory::getParentId, parentId);
        return this.page(page, queryWrapper);
//        return pmsProductCategoryMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean updateNavStatus(List<Integer> ids, Integer navStatus) {
        UpdateWrapper<PmsProductCategory> pmsProductCategoryUpdateWrapper = new UpdateWrapper<>();
        pmsProductCategoryUpdateWrapper.lambda()
                .set(PmsProductCategory::getNavStatus,navStatus)
                .in(PmsProductCategory::getId,ids);
        return this.update(pmsProductCategoryUpdateWrapper);
    }

    @Override
    public boolean updateShowStatus(List<Integer> ids, Integer showStatus) {
        UpdateWrapper<PmsProductCategory> pmsProductCategoryUpdateWrapper = new UpdateWrapper<>();
        pmsProductCategoryUpdateWrapper.lambda()
                .set(PmsProductCategory::getShowStatus,showStatus)
                .in(PmsProductCategory::getId,ids);
        return this.update(pmsProductCategoryUpdateWrapper);
    }

    @Override
    public boolean deleteById(Long id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean customerSave(ProductCategoryDTO productCategoryDTO) {

        PmsProductCategory productCategory = new PmsProductCategory();
        BeanUtils.copyProperties(productCategoryDTO,productCategory);
        List<Long> productAttributeIdList = productCategoryDTO.getProductAttributeIdList();

        productCategory.setProductCount(0);
        if(productCategory.getParentId() == 0) {
            productCategory.setLevel(0);
        } else {
            productCategory.setLevel(1);
        }
        this.save(productCategory);

        return saveRelation(productCategory, productAttributeIdList);
    }

    private boolean saveRelation(PmsProductCategory productCategory, List<Long> productAttributeIdList) {
        List<PmsProductCategoryAttributeRelation> relationList = new ArrayList<>();
        productAttributeIdList.forEach(attrId->{
            PmsProductCategoryAttributeRelation relation = new PmsProductCategoryAttributeRelation();
            relation.setProductAttributeId(attrId);
            relation.setProductCategoryId(productCategory.getId());
            relationList.add(relation);
        });
        relationService.saveBatch(relationList);

        return true;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean update(ProductCategoryDTO productCategoryDTO) {
        PmsProductCategory productCategory = new PmsProductCategory();
        BeanUtils.copyProperties(productCategoryDTO,productCategory);
        List<Long> productAttributeIdList = productCategoryDTO.getProductAttributeIdList();
        //保存商品分类
        if(productCategory.getParentId() == 0) {
            productCategory.setLevel(0);
        } else {
            productCategory.setLevel(1);
        }
        this.updateById(productCategory);
        //删除以保存的关联属性
        QueryWrapper<PmsProductCategoryAttributeRelation> queryWraper = new QueryWrapper<>();
        queryWraper.lambda().eq(PmsProductCategoryAttributeRelation::getProductCategoryId, productCategoryDTO.getId());
        relationService.remove(queryWraper);
        //添加新的关联属性
        return saveRelation(productCategory, productAttributeIdList);
    }

    @Override
    public List<ProductCateWithChildrenDTO> getWithChildren() {
        return pmsProductCategoryMapper.getWithChildren();
    }

}
