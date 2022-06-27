package com.tulingxueyuan.mall.modules.ums.service;

import com.tulingxueyuan.mall.modules.ums.model.UmsMemberReceiveAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 会员收货地址表 服务类
 * </p>
 *
 * @author XuShu
 * @since 2022-06-14
 */
public interface UmsMemberReceiveAddressService extends IService<UmsMemberReceiveAddress> {

    Boolean add(UmsMemberReceiveAddress address);

    Boolean edit(UmsMemberReceiveAddress address);

    Boolean delete(Long ids);

    List<UmsMemberReceiveAddress> listByMemberId();
}
