package com.tulingxueyuan.mall.common.util;

import cn.hutool.crypto.digest.BCrypt;

public class JiabinTest {

    public static void main(String[] args) {
        String encodePassword = BCrypt.hashpw("123456");
        System.out.println(encodePassword);
    }
}
