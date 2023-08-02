package com.Ambition.service;

import com.Ambition.dto.ResultData;

import java.util.List;

public interface RoleService {
    //获取所有角色和角色权限
    ResultData GetAllRole();
    //添加角色和角色权限
    ResultData insertRole(String roleName, List<Integer> permissionIdList,Integer salary);
    //修改角色和角色权限
    ResultData updateRole(String roleName, List<Integer> permissionIdList,Integer roleId);
    //通过角色id删除角色
    ResultData deleteRole(int roleId);
    //获取所有角色
    ResultData showAllRole();
}
