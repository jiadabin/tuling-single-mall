package com.tulingxueyuan.mall.modules.ums.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tulingxueyuan.mall.common.exception.Asserts;
import com.tulingxueyuan.mall.modules.ums.model.UmsMember;
import com.tulingxueyuan.mall.modules.ums.mapper.UmsMemberMapper;
import com.tulingxueyuan.mall.modules.ums.service.UmsMemberCacheService;
import com.tulingxueyuan.mall.modules.ums.service.UmsMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-06-14
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {

    @Autowired
    UmsMemberCacheService memberCacheService;

    @Override
    public UmsMember register(UmsMember umsMemberParam) {
        UmsMember umsMember = new UmsMember();
        BeanUtils.copyProperties(umsMemberParam, umsMember);
        umsMember.setCreateTime(new Date());
        umsMember.setStatus(1);
        //查询是否有相同用户名的用户
        QueryWrapper<UmsMember> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsMember::getUsername,umsMember.getUsername());
        List<UmsMember> umsAdminList = list(wrapper);
        if (umsAdminList.size() > 0) {
            return null;
        }

        //将密码进行加密操作
        String encodePassword = BCrypt.hashpw(umsMember.getPassword());
        umsMember.setPassword(encodePassword);
        baseMapper.insert(umsMember);
        return umsMember;

    }

    @Override
    public UmsMember login(String username, String password) {
        //密码需要客户端加密后传递
        UmsMember umsAdmin=null;
        umsAdmin = getAdminByUsername(username);
        try {
            if (!BCrypt.checkpw(password, umsAdmin.getPassword())){
                Asserts.fail("密码不正确");
            }
        } catch (Exception e) {
            Asserts.fail("登录异常：" + e.getMessage());
        }
        /*try {
            UserDetails userDetails =  loadUserByUsername(username);
            umsAdmin=((MemberDetails)userDetails).getUmsMember();

            if(!passwordEncoder.matches(password,umsAdmin.getPassword())){
                Asserts.fail("密码不正确");
            }

            // 生成springsecurity的通过认证标识
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            if(!userDetails.isEnabled()){
                Asserts.fail("帐号已被禁用");
            }
            insertLoginLog(username);
        } catch (Exception e) {
            Asserts.fail("登录异常:"+e.getMessage());
        }*/
        return umsAdmin;

    }

    private UmsMember getAdminByUsername(String username) {
        UmsMember user = memberCacheService.getUser(username);
        if(user!=null) return  user;
        QueryWrapper<UmsMember> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsMember::getUsername,username);
        List<UmsMember> adminList = list(wrapper);
        if (adminList != null && adminList.size() > 0) {
            user = adminList.get(0);
            memberCacheService.setUser(user);
            return user;
        }
        return null;
    }
}
