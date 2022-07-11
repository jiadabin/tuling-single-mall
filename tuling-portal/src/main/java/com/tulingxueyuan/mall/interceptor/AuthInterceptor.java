package com.tulingxueyuan.mall.interceptor;

import cn.hutool.core.util.StrUtil;
import com.tulingxueyuan.mall.common.api.ResultCode;
import com.tulingxueyuan.mall.common.exception.ApiException;
import com.tulingxueyuan.mall.common.util.ComConstants;
import com.tulingxueyuan.mall.common.util.JwtTokenUtil;
import com.tulingxueyuan.mall.modules.ums.model.UmsMember;
import com.tulingxueyuan.mall.modules.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 作用： 验证 用户是否登录、菜单资源权限
 * 作者：徐庶
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    // 配置文件中的白名单secure.ignored.urls
    private List<String> urls;

    @Autowired
    private UmsMemberService umsMemberService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1、不需要登录就可以访问的路径——白名单
        // 获取当前请求   /admin/login
        String requestURI = request.getRequestURI();
        // Ant方式路径匹配 /**  ？  _
        PathMatcher matcher = new AntPathMatcher();
        for (String ignoredUrl : urls) {
            if(matcher.match(ignoredUrl,requestURI)){
                return true;
                //如果是白名单，说明不需要登录也能访问
            }
        }

        //拿到jwt令牌
        String jwt = request.getHeader(tokenHeader);
        //判断是否存在 判断开头是否加了tokenHead
        if( StrUtil.isBlank(jwt) || !jwt.startsWith(tokenHead) ) {
            throw new ApiException(ResultCode.UNAUTHORIZED);
        }

        //解密
        jwt = jwt.substring(tokenHead.length());
        String username = jwtTokenUtil.getUserNameFromToken(jwt);

        if (StrUtil.isBlank(username)) {
            throw new ApiException(ResultCode.UNAUTHORIZED);
        }
        //从服务器中查询
        UmsMember umsMember = umsMemberService.getMemberByUsername(username);
        if( umsMember == null ) {
            throw new ApiException(ResultCode.UNAUTHORIZED);
        }

//        JwtTokenUtil.currentUserName.set(username);

        return true;
    }


    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
