package com.mt;
/*
 * {@code @author} ma
 * {@code @date} 2024/12/23 19:08
 * {@code @project} BankSecuritySystem
 *
 */


import com.mt.utils.EmailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code @projectName:}    BankSecuritySystem
 * {@code @package:}        PACKAGE_NAME
 * {@code @className:}      com.mt.test01
 * {@code @author:}         ma
 * {@code @date:}           2024/12/23 19:08
 * {@code @description:}
 */

@SpringBootTest
public class test01 {


    @Autowired
    private EmailUtil emailUtil;

    @Test
    public void test01() {
        String to = "3130782490@qq.com";
        emailUtil.sendSimpleEmail(to, "", "hello worldss");
    }
}
