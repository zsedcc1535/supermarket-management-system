package com.Ambition;

import com.Ambition.mapper.PermissionMapper;
import com.Ambition.pojo.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SupermarketApplicationTests {


    @Resource
    private PermissionMapper permissionMapper;

    @Test
    void contextLoads() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }
    @Test
    void test(){
        List<Permission> permissions = permissionMapper.GetPermissionByRoleId(1);
        System.out.println(permissions);
    }
}
