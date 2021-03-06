package com.tulingxueyuan.mall.modules.ums.service;

import com.tulingxueyuan.mall.modules.ums.model.UmsMember;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author XuShu
 * @since 2022-06-14
 */
public interface UmsMemberService extends IService<UmsMember> {

    UmsMember register(UmsMember umsMemberParam);

    UmsMember login(String username, String password);

    UmsMember getCurrentMember();

    UmsMember getMemberByUsername(String username);

    UserDetails loadUserByUsername(String username);

    UmsMember getAdminByUsername(String username);
}
