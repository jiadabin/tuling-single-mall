package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.RelationAttrInfoDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2022-05-19
 */
@RestController
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {

    @Autowired
    private PmsProductAttributeService pmsProductAttributeService;

    /**
     * return request({
     *     url:'/productAttribute/list/'+cid,
     *     method:'get',
     *     params:params
     *   })
     */
    @RequestMapping(value = "/list/{cid}",method = RequestMethod.GET)
    public CommonResult<CommonPage> getList(
            @PathVariable Long cid,
            @RequestParam(value = "type") Integer type,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        Page page = pmsProductAttributeService.list(cid, type, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(page));
    }

    /**
     * return request({
     *     url:'/productAttribute/'+id,
     *     method:'get'
     *   })
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CommonResult getById(@PathVariable Long id) {

        PmsProductAttribute pmsProductAttribute = pmsProductAttributeService.getById(id);
        return CommonResult.success(pmsProductAttribute);
    }

    /**
     * return request({
     *     url:'/productAttribute/create',
     *     method:'post',
     *     data:data
     *   })
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult create(@RequestBody PmsProductAttribute pmsProductAttribute) {

        boolean result = pmsProductAttributeService.add(pmsProductAttribute);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * return request({
     *     url:'/productAttribute/update/'+id,
     *     method:'post',
     *     data:data
     *   })
     */
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public CommonResult update(@RequestBody PmsProductAttribute pmsProductAttribute) {

        boolean result = pmsProductAttributeService.updateById(pmsProductAttribute);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * return request({
     *     url:'/productAttribute/delete',
     *     method:'post',
     *     data:data
     *   })
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public CommonResult delete(@RequestParam(name = "ids") List<Long> ids) {

        boolean result = pmsProductAttributeService.delete(ids);

        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * return request({
     *     url:'/productAttribute/attrInfo/'+productCategoryId,
     *     method:'get'
     *   })
     */
    @RequestMapping(value = "/attrInfo/{cid}",method = RequestMethod.GET)
    public CommonResult getRelationAttrByCid(@PathVariable Long cid) {

        List<RelationAttrInfoDTO> relationList = pmsProductAttributeService.getRelationAttrByCid(cid);
        return CommonResult.success(relationList);
    }
}

