package com.tulingxueyuan.mall.config;

import com.tulingxueyuan.config.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity  //启动SpringSecurity
public class MallSecurityConfig extends SecurityConfig {
}
