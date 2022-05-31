package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductParamDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2022-05-19
 */
@RestController
@RequestMapping("/product")
public class PmsProductController {

    @Autowired
    private PmsProductService productService;

    /**
     * url:'/product/list',
     *     method:'get',
     *     params:params
     *
     *     axios如果设置的是data属性就是以json的方式传递
     *     axios如果设置的是params属性就是以url的方式传递
     *     axios如果设置的是URLSearchParams属性就是以formdata的方式传递
     * @return
     */
    @ApiOperation("商品列表")
    @RequestMapping(value = "/list", method= RequestMethod.GET)
    public CommonResult list(ProductParamDTO param) {

        Page page = productService.list(param);

        return CommonResult.success(CommonPage.restPage(page));
    }

    /**
     * url:'/product/update/deleteStatus',
     *     method:'post',
     *     params:params
     */
    @RequestMapping(value = "/update/deleteStatus", method = RequestMethod.POST)
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        boolean result = productService.removeByIds(ids);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * url:'/product/update/newStatus',
     *     method:'post',
     *     params:params
     */
    @RequestMapping(value = "/update/newStatus", method = RequestMethod.POST)
    public CommonResult updateNewStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("newStatus") Integer newStatus) {
        boolean result = productService.updateStatus(ids, newStatus, PmsProduct::getNewStatus);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * url:'/product/update/recommendStatus',
     *     method:'post',
     *     params:params
     */
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.POST)
    public CommonResult updateRecommendStatus(@RequestParam("ids") List<Long> ids,
                                              @RequestParam("recommendStatus") Integer recommendStatus) {
        boolean result = productService.updateStatus(ids, recommendStatus, PmsProduct::getRecommandStatus);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * url:'/product/update/publishStatus',
     *     method:'post',
     *     params:params
     */
    @RequestMapping(value = "/update/publishStatus", method = RequestMethod.POST)
    public CommonResult updatePublishStatus(@RequestParam("ids") List<Long> ids,
                                            @RequestParam("publishStatus") Integer publishStatus) {
        boolean result = productService.updateStatus(ids, publishStatus, PmsProduct::getPublishStatus);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }
}

