package com.manage.seatManage;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@SpringBootTest

class UserCentreApplicationTests {

    public void testSelect() {
        String newPassword = DigestUtils.md5DigestAsHex(("abcd"+"my").getBytes(StandardCharsets.UTF_8));
        System.out.println(newPassword);
    }

}
