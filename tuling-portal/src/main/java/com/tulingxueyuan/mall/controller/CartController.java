package com.tulingxueyuan.mall.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.AddCartDTO;
import com.tulingxueyuan.mall.modules.oms.service.OmsCartItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "CartController", description = "购物车服务接口")
@RequestMapping("/cart")
public class CartController {

    @Autowired
    OmsCartItemService cartItemService;

    /**
     * .post("/car/add", {
     *           productId: this.id,
     *           productSkuId: this.skuId,
     *           quantity: 1,
     *         })
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonResult add(@RequestBody AddCartDTO addCartDTO) {
        Boolean result=cartItemService.add(addCartDTO);
        if(result){
            return  CommonResult.success(result);
        }
        else {
            return  CommonResult.failed();
        }
    }

    /**
     *  初始化状态栏的购物车商品数量
     *   this.axios.get('/car/products/sum').then((res=0)=>{
     */
    @RequestMapping(value="/products/sum",method = RequestMethod.GET)
    public CommonResult getCarProdutSum(){
        Integer count= cartItemService.getCarProdutSum();
        return CommonResult.success(count);
    }
}
