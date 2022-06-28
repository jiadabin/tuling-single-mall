package com.tulingxueyuan.mall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.common.exception.ApiException;
import com.tulingxueyuan.mall.dto.ConfirmOrderDTO;
import com.tulingxueyuan.mall.dto.OrderDetailDTO;
import com.tulingxueyuan.mall.dto.OrderListDTO;
import com.tulingxueyuan.mall.dto.OrderParamDTO;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrder;
import com.tulingxueyuan.mall.modules.oms.service.OmsOrderService;
import com.tulingxueyuan.mall.modules.oms.service.TradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "OrderController",description = "订单服务接口")
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    OmsOrderService orderService;
    @Autowired
    TradeService tradeService;

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

    /**
     *  生成订单(下单）
     * this.axios
     *           .post("/order/generateOrder", {
     */
    @RequestMapping(value="/generateOrder",method = RequestMethod.POST)
    public CommonResult generateOrder(@RequestBody OrderParamDTO paramDTO){
        OmsOrder omsOrder = orderService.generateOrder(paramDTO);
        return CommonResult.success(omsOrder.getId());
    }

    /**
     *  读取下单成功后的订单详情
     * this.axios.get(`/order/orderDetail?orderId=${this.orderId}`).then((res)=>{
     */
    @RequestMapping(value="/orderDetail")
    public CommonResult getOrderDetail(@RequestParam("orderId")Long id){
        OrderDetailDTO dto=orderService.getOrderDetail(id);
        return  CommonResult.success(dto);
    }

    /**
     *  我的订单列表
     * this.axios.post('/order/list/userOrder',Qs.stringify({
     pageSize:10,
     pageNum:this.pageNum
     */
    @RequestMapping(value="/list/userOrder",method = RequestMethod.POST)
    public CommonResult getMyOrders(
            @RequestParam(value="pageSize",defaultValue = "10")Integer pageSize,
            @RequestParam(value="pageNum",defaultValue = "1")Integer pageNum) {
        IPage<OrderListDTO> myOrders = orderService.getMyOrders(pageSize, pageNum);

        return CommonResult.success(myOrders);
    }

    /**
     * 生成当面付二维码
     *  /order/tradeQrCode
     orderId:this.orderId,
     payType:1
     */
    @ApiOperation("支付接口，只实现支付宝支付，微信支付暂未实现")
    @ApiImplicitParams({@ApiImplicitParam(name="orderId",value = "订单id"),
                        @ApiImplicitParam(name="payType",value = "支付类型1:支付宝2：微信",allowableValues = "1,2",dataType = "integer")})
    @RequestMapping(value="tradeQrCode",method = RequestMethod.POST)
    public  CommonResult tradeQrCode(@RequestParam("orderId") Long orderId,
                                     @RequestParam("payType") Integer payType){
        if(payType>2 || payType<0){
            throw new ApiException("支付类型参数错误！");
        }
        return tradeService.tradeQrCode(orderId,payType);
    }
}
