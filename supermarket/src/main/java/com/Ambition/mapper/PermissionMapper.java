package com.Ambition.mapper;

import com.Ambition.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {
    //  获取所有权限
    List<Permission> GetAllPermission();
    //根据角色id获取对应权限
    List<Permission> GetPermissionByRoleId(Integer roleId);
}
