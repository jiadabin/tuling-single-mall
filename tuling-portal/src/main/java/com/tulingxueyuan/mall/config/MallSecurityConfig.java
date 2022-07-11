package com.tulingxueyuan.mall.config;

import com.tulingxueyuan.config.SecurityConfig;
import com.tulingxueyuan.mall.modules.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity  //启动SpringSecurity
public class MallSecurityConfig extends SecurityConfig {

    @Autowired
    private UmsMemberService memberService;

    /**
     * 认证交给springsecurity
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> memberService.loadUserByUsername(username);
    }
}
