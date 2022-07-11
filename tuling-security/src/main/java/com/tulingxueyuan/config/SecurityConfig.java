package com.tulingxueyuan.config;

import com.tulingxueyuan.config.component.JwtAuthenticationFilter;
import com.tulingxueyuan.config.component.RestfulAccessDeniedHandler;
import com.tulingxueyuan.config.component.RestfulAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();

        //循环白名单进行放行
        for (String url : ignoredUrlsConfig().getUrls()) {
            registry.antMatchers(url).permitAll();
        }

        // 允许可以请求OPTIONS CORS
        registry.antMatchers(HttpMethod.OPTIONS).permitAll();

        // 其他任何请求都需要身份认证
        registry
                // 任何请求
                .anyRequest()
                // 都需要认证
                .authenticated()
                // 关闭csrf跨站请求伪造 :因为现在使用jwt来实现认证
                .and()
                // 支持跨域
                .cors()
                .and()
                .csrf()
                .disable()
                // 禁止session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 自定义权限拒绝处理类
                .and()
                .exceptionHandling()
                // 没有权限访问时的处理类
                .accessDeniedHandler(restfulAccessDeniedHandler())
                // 没有登录时的处理类
                .authenticationEntryPoint(restfulAuthenticationEntryPoint())
                .and()
                // 加入jwt认证过滤器
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IgnoredUrlsConfig ignoredUrlsConfig(){
        return new IgnoredUrlsConfig();
    }

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler(){
        return new RestfulAccessDeniedHandler();
    }


    @Bean
    public RestfulAuthenticationEntryPoint restfulAuthenticationEntryPoint(){
        return new RestfulAuthenticationEntryPoint();
    }
}
