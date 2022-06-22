package com.tulingxueyuan.mall.modules.oms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tulingxueyuan.mall.common.api.ResultCode;
import com.tulingxueyuan.mall.common.exception.ApiException;
import com.tulingxueyuan.mall.common.service.RedisService;
import com.tulingxueyuan.mall.dto.ConfirmOrderDTO;
import com.tulingxueyuan.mall.modules.oms.mapper.OmsCartItemMapper;
import com.tulingxueyuan.mall.modules.oms.model.OmsCartItem;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrder;
import com.tulingxueyuan.mall.modules.oms.mapper.OmsOrderMapper;
import com.tulingxueyuan.mall.modules.oms.service.OmsCartItemService;
import com.tulingxueyuan.mall.modules.oms.service.OmsOrderItemService;
import com.tulingxueyuan.mall.modules.oms.service.OmsOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.oms.service.OmsOrderSettingService;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductService;
import com.tulingxueyuan.mall.modules.pms.service.PmsSkuStockService;
import com.tulingxueyuan.mall.modules.ums.model.UmsMember;
import com.tulingxueyuan.mall.modules.ums.model.UmsMemberReceiveAddress;
import com.tulingxueyuan.mall.modules.ums.service.UmsMemberReceiveAddressService;
import com.tulingxueyuan.mall.modules.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-06-21
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements OmsOrderService {

    @Autowired
    private OmsCartItemService cartItemService;
    @Autowired
    private PmsProductService productService;
    @Autowired
    private UmsMemberReceiveAddressService addressService;
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    OmsCartItemMapper cartItemMapper;
    @Autowired
    RedisService redisService;
//    @Value("${redis.key.prefix.orderId}")
//    private String REDIS_KEY_PREFIX_ORDER_ID;
    @Autowired
    OmsOrderItemService orderItemService;
    @Autowired
    PmsSkuStockService skuStockService;
    @Autowired
    OmsOrderSettingService orderSettingService;

    @Override
    public ConfirmOrderDTO generateConfirmOrder(List<Long> ids) {
        if(CollectionUtil.isEmpty(ids)){
            throw new ApiException(ResultCode.VALIDATE_FAILED);
        }
        ConfirmOrderDTO confirmOrderDTO = new ConfirmOrderDTO();
        // 1.商品
        List<OmsCartItem> omsCartItemsList = cartItemService.listByIds(ids);
        confirmOrderDTO.setCartList(omsCartItemsList);

        // 2、计算价格
        calcCatAmount(confirmOrderDTO);
        // 3.收货地址
        UmsMember currentMember = memberService.getCurrentMember();
        QueryWrapper<UmsMemberReceiveAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UmsMemberReceiveAddress::getMemberId,currentMember.getId());
        List<UmsMemberReceiveAddress> list = addressService.list(queryWrapper);
        confirmOrderDTO.setAddressList(list);

        return confirmOrderDTO;
    }

    /**
     * 计算价格
     * @param confirmOrderDTO
     */
    public void calcCatAmount(ConfirmOrderDTO confirmOrderDTO){
        //计算商品数量
        Integer productTotal=0;
        // 总价
        BigDecimal priceTotal=new BigDecimal(0);
        // 运费
        BigDecimal freightAmount=new BigDecimal(0);

        for (OmsCartItem omsCartItem : confirmOrderDTO.getCartList()) {
            // 商品总件数
            productTotal+=omsCartItem.getQuantity();
            // 总价
            priceTotal= priceTotal.add( omsCartItem.getPrice().multiply(new BigDecimal(omsCartItem.getQuantity())));

            PmsProduct product = productService.getById(omsCartItem.getProductId());
            String serviceIds = product.getServiceIds();
            String[] serviceIdsArray = serviceIds.split(",");
            if(serviceIdsArray.length>0){
                // 判断是否包邮
                if(!ArrayUtil.containsAny(serviceIdsArray, "3")){
                    freightAmount=freightAmount.add(new BigDecimal(10));
                }
            }

        }
        confirmOrderDTO.setProductTotal(productTotal);
        confirmOrderDTO.setPriceTotal(priceTotal);
        confirmOrderDTO.setFreightAmount(freightAmount);
        confirmOrderDTO.setPayAmount(priceTotal.subtract(freightAmount));
    }
}
