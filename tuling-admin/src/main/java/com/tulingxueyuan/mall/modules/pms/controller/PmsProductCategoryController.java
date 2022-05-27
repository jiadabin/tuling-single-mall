package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductCateWithChildrenDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductCategoryDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产品分类 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2022-05-19
 */
@RestController
@RequestMapping("/productCategory")
public class PmsProductCategoryController {

    @Autowired
    private PmsProductCategoryService pmsProductCategoryService;

    @RequestMapping(value = "/list/{parentId}",method = RequestMethod.GET)
    public CommonResult<CommonPage<PmsProductCategory>> getList(@PathVariable Long parentId,
                                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        Page page = pmsProductCategoryService.list(parentId, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @RequestMapping(value = "/update/navStatus",method = RequestMethod.POST)
    public CommonResult updateNavStatus(@RequestParam(value = "ids") List<Integer> ids,
                                        @RequestParam(value = "navStatus") Integer navStatus) {

        boolean result = pmsProductCategoryService.updateNavStatus(ids, navStatus);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/update/showStatus",method = RequestMethod.POST)
    public CommonResult updateShowStatus(@RequestParam(value = "ids") List<Integer> ids,
                                        @RequestParam(value = "showStatus") Integer showStatus) {

        boolean result = pmsProductCategoryService.updateShowStatus(ids, showStatus);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    public CommonResult delete(@PathVariable Long id) {

        boolean result = pmsProductCategoryService.deleteById(id);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id, @RequestBody ProductCategoryDTO productCategoryDTO) {
        productCategoryDTO.setId(id);
        boolean result = pmsProductCategoryService.update(productCategoryDTO);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CommonResult getById(@PathVariable Long id) {

        PmsProductCategory category = pmsProductCategoryService.getById(id);
        return CommonResult.success(category);
    }

    /**
     * return request({
     *     url:'/productCategory/create',
     *     method:'post',
     *     data:data
     *   })
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult create(@RequestBody ProductCategoryDTO productCategoryDTO) {

        boolean result = pmsProductCategoryService.customerSave(productCategoryDTO);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 获取商品的一级分类和二级分类的级联数据
     * url:'/productCategory/list/withChildren',
     *     method:'get'
     */
    @RequestMapping(value = "/list/withChildren",method = RequestMethod.GET)
    public CommonResult listWithChildren() {

        List<ProductCateWithChildrenDTO> list = pmsProductCategoryService.getWithChildren();
        return CommonResult.success(list);
    }
}

