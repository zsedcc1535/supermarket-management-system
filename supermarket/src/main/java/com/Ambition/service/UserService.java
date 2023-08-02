package com.Ambition.service;

import com.Ambition.dto.ResultData;
import com.Ambition.pojo.User;

import java.util.List;


public interface UserService {
    //获取所有用户
    ResultData GetAllUser();
    //根据xxx获取用户
    User GetUserBy(String userCode, String password);
    //删除某个用户
    ResultData deleteUser(Integer id);
    //增加用户
    ResultData insertUser(User user);
    //修改用户信息
    ResultData updateUser(User user);
    //查询用户
    ResultData searchUser(User user);
    //批量删除
    ResultData deleteUserList(List<Integer> userIdlist);
    //发送邮件
    void Email(String value ,Integer userId,String email);
}
