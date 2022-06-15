package com.tulingxueyuan.mall.interceptor;

import com.tulingxueyuan.mall.common.api.ResultCode;
import com.tulingxueyuan.mall.common.exception.ApiException;
import com.tulingxueyuan.mall.common.util.ComConstants;
import com.tulingxueyuan.mall.modules.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
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

        //2、未登录用户，直接拒绝访问
        if (null == request.getSession().getAttribute(ComConstants.FLAG_MEMBER_USER)) {
            throw new ApiException(ResultCode.UNAUTHORIZED);
        }

        return true;
    }


    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
