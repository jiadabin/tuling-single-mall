package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.modules.pms.model.PmsBrand;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsBrandMapper;
import com.tulingxueyuan.mall.modules.pms.service.PmsBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-19
 */
@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements PmsBrandService {

    @Autowired
    private PmsBrandMapper brandMapper;

    @Override
    public Page list(Integer keyword, Integer pageNum, Integer pageSize) {

        Page page = new Page(pageNum, pageSize);
        QueryWrapper<PmsBrand> queryWraper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)){
            queryWraper.lambda().like(PmsBrand::getName, keyword);
        }
        queryWraper.lambda().orderByAsc(PmsBrand::getSort);
        return this.page(page, queryWraper);
    }
}
