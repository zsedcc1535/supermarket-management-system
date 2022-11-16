package com.Ambition.mapper;

import com.Ambition.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
//  获取所有角色和和权限
    List<Role> GetAllRole();
//  根据角色名获取角色
    Role GetRoleBy(String rolename,Integer id);
//  添加角色
    void insertRole(Role role);
//  修改角色
    void updateRole(Role role);
    //删除角色
    void deleteRole(Integer id);
    //展示所有角色
    List<Role> showAllRole();
}
