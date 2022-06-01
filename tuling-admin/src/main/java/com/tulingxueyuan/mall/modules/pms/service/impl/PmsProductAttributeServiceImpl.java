package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductAttributeCategoryMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductAttributeMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategoryAttributeRelation;
import com.tulingxueyuan.mall.modules.pms.model.dto.RelationAttrInfoDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryAttributeRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-19
 */
@Service
public class PmsProductAttributeServiceImpl extends ServiceImpl<PmsProductAttributeMapper, PmsProductAttribute> implements PmsProductAttributeService {

    @Autowired
    private PmsProductAttributeMapper pmsProductAttributeMapper;

    @Autowired
    private PmsProductAttributeCategoryService pmsProductAttributeCategoryService;

    @Autowired
    private PmsProductCategoryAttributeRelationService relationService;

    @Override
    public Page list(Long cid, Integer type, Integer pageNum, Integer pageSize) {
        Page page = new Page(pageNum, pageSize);

        QueryWrapper<PmsProductAttribute> queryWraper = new QueryWrapper<>();
        queryWraper.lambda().eq(PmsProductAttribute::getProductAttributeCategoryId, cid)
                            .eq(PmsProductAttribute::getType, type)
                            .orderByAsc(PmsProductAttribute::getSort);
        return this.page(page, queryWraper);
    }

    @Override
    public boolean add(PmsProductAttribute pmsProductAttribute) {

//        PmsProductAttributeCategory pmsProductAttributeCategory = pmsProductAttributeCategoryMapper.selectById(pmsProductAttribute.getProductAttributeCategoryId());
//        if(pmsProductAttribute.getType() == 0){
//            pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount() + 1);
//        }else{
//            pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount() + 1);
//        }
//
//        pmsProductAttributeCategoryMapper.updateById(pmsProductAttributeCategory);
        boolean save = this.save(pmsProductAttribute);
        if (save){
            UpdateWrapper<PmsProductAttributeCategory> updateWraper = new UpdateWrapper();
            if(pmsProductAttribute.getType() == 0){
                updateWraper.setSql("attribute_count = attribute_count + 1");
            }else {
                updateWraper.setSql("param_count = param_count + 1");
            }
            updateWraper.lambda().eq(PmsProductAttributeCategory::getId, pmsProductAttribute.getProductAttributeCategoryId());
            pmsProductAttributeCategoryService.update(updateWraper);
        }
        return save;
    }

    @Override
    @Transactional
    public boolean delete(List<Long> ids) {

        if (CollectionUtils.isEmpty(ids)){
            return false;
        }

        PmsProductAttribute pmsProductAttribute = null;
        for (Long id : ids) {
            pmsProductAttribute = this.getById(id);
            if (pmsProductAttribute != null){
                break;
            }
        }

        int length = pmsProductAttributeMapper.deleteBatchIds(ids);

        if (length > 0 && pmsProductAttribute != null){
            UpdateWrapper<PmsProductAttributeCategory> updateWraper = new UpdateWrapper();
            if(pmsProductAttribute.getType() == 0){
                updateWraper.setSql("attribute_count = attribute_count - " + length);
            }else {
                updateWraper.setSql("param_count = param_count - " + length);
            }
            updateWraper.lambda().eq(PmsProductAttributeCategory::getId, pmsProductAttribute.getProductAttributeCategoryId());
            pmsProductAttributeCategoryService.update(updateWraper);
        }
        return length > 0;
    }

    @Override
    public List<RelationAttrInfoDTO> getRelationAttrByCid(Long cid) {
        /*List<RelationAttrInfoDTO> DTOList = new ArrayList<>();
        QueryWrapper<PmsProductCategoryAttributeRelation> queryWraper = new QueryWrapper<>();
        queryWraper.lambda().eq(PmsProductCategoryAttributeRelation::getProductCategoryId, cid);
        List<PmsProductCategoryAttributeRelation> list = relationService.list(queryWraper);
        list.forEach(relation->{
            RelationAttrInfoDTO dto = new RelationAttrInfoDTO();
            dto.setAttributeCategoryId(relation.getProductCategoryId());
            dto.setAttributeId(relation.getProductAttributeId());
            DTOList.add(dto);
        });*/
        List<RelationAttrInfoDTO> list = pmsProductAttributeMapper.getRelationAttrByCid(cid);
        return list;
    }

}
