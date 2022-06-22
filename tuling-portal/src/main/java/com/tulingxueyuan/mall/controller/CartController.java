package com.tulingxueyuan.mall.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.AddCartDTO;
import com.tulingxueyuan.mall.dto.CartItemStockDTO;
import com.tulingxueyuan.mall.modules.oms.service.OmsCartItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 获取购物车数据初始化
     * this.axios.get('/cart/list')
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public CommonResult getList(){
        List<CartItemStockDTO> list= cartItemService.getList();

        return CommonResult.success(list);
    }

    /**
     *  更新商品数量
     *  this.axios.post('/cart/update/quantity',Qs.stringify({
     *             id:item.id,
     *             quantity:item.quantity   当前数量
     *           }),{headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
     */
    @RequestMapping(value="/update/quantity",method = RequestMethod.POST)
    public CommonResult updateQuantity(
            @RequestParam Long id,
            @RequestParam Integer quantity){
        Boolean result= cartItemService.updateQuantity(id,quantity);
        if(result){
            return  CommonResult.success(result);
        }
        else {
            return  CommonResult.failed();
        }
    }

    /**
     *  删除
     *  this.axios.post('/cart/delete',Qs.stringify({
     *             ids:item.id
     *           }),{headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    public CommonResult delete(
            @RequestParam Long ids){
        Boolean result= cartItemService.delete(ids);
        if(result){
            return  CommonResult.success(result);
        }
        else {
            return  CommonResult.failed();
        }
    }
}
