package com.Ambition.mapper;

import com.Ambition.pojo.RolePermission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RolePermissionMapper {
    //插入角色权限
    void insertRolePermissio(RolePermission rolePermission);
    //修删除角色权限
    void deleteRolePermissio(Integer roleId);
}
