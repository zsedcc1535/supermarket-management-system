package com.Ambition.service.impl;


import com.Ambition.dto.UserLogin;
import com.Ambition.mapper.PermissionMapper;
import com.Ambition.mapper.UserMapper;
import com.Ambition.pojo.Permission;
import com.Ambition.pojo.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        User user = userMapper.GetUserBy(null,username,null);
        //异常
        if (Objects.isNull(user)){
            throw new UsernameNotFoundException("用户名未发现");
        }

        //查询对应权限信息
        List<Permission> permissions = permissionMapper.GetPermissionByRoleId(user.getRoleId());
        ArrayList<String> strings = new ArrayList<>();
        for (Permission permission : permissions) {
            strings.add(permission.getPermissionName());
        }

        //数据封装为UserDetails返回
        return new UserLogin(user,strings);



    }
}