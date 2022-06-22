package com.tulingxueyuan.mall.modules.oms.service;

import com.tulingxueyuan.mall.dto.ConfirmOrderDTO;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author XuShu
 * @since 2022-06-21
 */
public interface OmsOrderService extends IService<OmsOrder> {

    ConfirmOrderDTO generateConfirmOrder(List<Long> ids);
}
