package com.tulingxueyuan.mall.modules.pms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductCategoryMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductParamDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-19
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {

    @Override
    public Page list(ProductParamDTO param) {


        Page page = new Page(param.getPageNum(), param.getPageSize());
        QueryWrapper<PmsProduct> queryWraper = new QueryWrapper<>();
        LambdaQueryWrapper<PmsProduct> lambda = queryWraper.lambda();
        if (!StrUtil.isBlank(param.getKeyword())){
            lambda.like(PmsProduct::getName, param.getKeyword());
        }
        if (!StrUtil.isBlank(param.getProductSn())){
            lambda.eq(PmsProduct::getProductSn, param.getProductSn());
        }
        if (param.getProductCategoryId() != null && param.getProductCategoryId() > 0){
            lambda.eq(PmsProduct::getProductCategoryId, param.getProductCategoryId());
        }
        if (param.getBrandId() != null && param.getBrandId() > 0){
            lambda.eq(PmsProduct::getBrandId, param.getBrandId());
        }
        if (param.getPublishStatus() != null){
            lambda.eq(PmsProduct::getPublishStatus, param.getPublishStatus());
        }
        if (param.getVerifyStatus() != null){
            lambda.eq(PmsProduct::getVerifyStatus, param.getVerifyStatus());
        }
        return this.page(page, queryWraper);
    }

    @Override
    public boolean updateStatus(List<Long> ids, Integer status, SFunction<PmsProduct, ?> getStatus) {
        UpdateWrapper<PmsProduct> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(getStatus, status)
                       .in(PmsProduct::getId, ids);
        return this.update(wrapper);
}

}
