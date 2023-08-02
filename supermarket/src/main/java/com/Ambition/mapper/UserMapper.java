package com.Ambition.mapper;

import com.Ambition.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Mapper
public interface UserMapper {
    //获取所有用户信息和用户角色
    List<User> GetAllUser();
    //根据xxx找到用户
    User GetUserBy(Integer id,String userCode,String password);
    //移除某个用户
    void deleteUser(Integer id);
    //增加用户
    void addUser(User user);
    //修改用户
    void updateUser(User user);
    //模糊查询用户
    List<User> GetUserLike(String userName,Integer roleId);
    //Security的数据库查询
    UserDetails GetSecurityUserBy(String userCode);
    //计算用户数量
    int CountUser();
}