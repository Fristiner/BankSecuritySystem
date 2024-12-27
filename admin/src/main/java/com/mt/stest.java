package com.mt;

import cn.hutool.core.lang.UUID;

public class stest {
    public static void main(String[] args) {
        String token = UUID.randomUUID().toString(true);
        System.out.println(token+"  "+token.length());
        String substring = token.substring(0, 16);
        System.out.println(substring+"  "+substring.length());
    }
}
