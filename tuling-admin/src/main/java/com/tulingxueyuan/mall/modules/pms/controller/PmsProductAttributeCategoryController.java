package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductAttributeCateDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2022-05-19
 */
@RestController
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {

    @Autowired
    private PmsProductAttributeCategoryService pmsProductAttributeCategoryService;

    /**
     * url:'/productAttribute/category/list',
     *     method:'get',
     *     params:params
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public CommonResult<CommonPage<PmsProductAttributeCategory>> getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                         @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        Page page = pmsProductAttributeCategoryService.list(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(page));
    }

    /**
     *
     * return request({
     *     url:'/productAttribute/category/create',
     *     method:'post',
     *     data:data
     *   })
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult create(PmsProductAttributeCategory pmsProductAttributeCategory) {

        boolean result = pmsProductAttributeCategoryService.add(pmsProductAttributeCategory);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * return request({
     *     url:'/productAttribute/category/delete/'+id,
     *     method:'get'
     *   })
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public CommonResult delete(@PathVariable Long id) {

        boolean result = pmsProductAttributeCategoryService.deleteById(id);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * return request({
     *     url:'/productAttribute/category/update/'+id,
     *     method:'post',
     *     data:data
     *   })
     */
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id, PmsProductAttributeCategory pmsProductAttributeCategory) {
        pmsProductAttributeCategory.setId(id);
        boolean result = pmsProductAttributeCategoryService.updateById(pmsProductAttributeCategory);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * return request({
     *     url:'/productAttribute/category/list/withAttr',
     *     method:'get'
     *   })
     */
    @RequestMapping(value = "/list/withAttr",method = RequestMethod.GET)
    public CommonResult getListWithAttr() {

        List<ProductAttributeCateDTO> list = pmsProductAttributeCategoryService.listWithAttr();
        return CommonResult.success(list);
    }
}

