package com.tulingxueyuan.mall.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.ConfirmOrderDTO;
import com.tulingxueyuan.mall.modules.oms.service.OmsOrderService;
import com.tulingxueyuan.mall.modules.oms.service.TradeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "OrderController",description = "订单服务接口")
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    OmsOrderService orderService;
//    @Autowired
//    TradeService tradeService;

    /**
     *  加入购物车---生成确认订单实现
     *  立即购买—生成确认订单实现 product_id  sku_id. 改成DTO接收
     *    复用业务逻辑的代码 product_id 和sku_id 查出购物车对象所需要信息
     *  初始化确认订单的商品和收货地址信息
     * this.axios.post('/order/generateConfirmOrder',Qs.stringify({itemIds: constStore.itemids}
     */
    @RequestMapping(value="generateConfirmOrder",method = RequestMethod.POST)
    public CommonResult generateConfirmOrder(
            @RequestParam("itemIds") List<Long> ids
    ){
        ConfirmOrderDTO confirmOrderDTO= orderService.generateConfirmOrder(ids);
        return CommonResult.success(confirmOrderDTO) ;
    }
}
