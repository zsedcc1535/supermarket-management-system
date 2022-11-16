package com.Ambition.service.impl;

import com.Ambition.Utils.Code;
import com.Ambition.Utils.Constant;
import com.Ambition.dto.ResultData;
import com.Ambition.mapper.RoleMapper;
import com.Ambition.mapper.RolePermissionMapper;
import com.Ambition.pojo.Role;
import com.Ambition.pojo.RolePermission;
import com.Ambition.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    //获取所有角色和角色的权限
    public ResultData GetAllRole(){
        ResultData<Object> resultData = new ResultData<>();
        List<Role> roles = roleMapper.GetAllRole();
        PageInfo<Role> pages = new PageInfo<>(roles);
        resultData.setCode(200);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", pages.getTotal());
        map.put("pages", pages.getPages());
        map.put("pageNum", pages.getPageNum());
        map.put("userList", pages.getList());
        resultData.setData(map);
        System.out.println("=================>执行GetAllRole方法");
        return resultData;
    }

    //插入角色和角色的权限
    public ResultData insertRole(String roleName, List<Integer> permissionIdList) {
        ResultData<Object> resultData = new ResultData<>();
        Role role = new Role();
        role.setRolename(roleName);
        try {
            roleMapper.insertRole(role);
            role = roleMapper.GetRoleBy(roleName,null);
            System.out.println("添加的角色ID" + role.getId());
            for (int permissionId : permissionIdList) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(role.getId());
                rolePermission.setPermissionId(permissionId);
                rolePermissionMapper.insertRolePermissio(rolePermission);
            }
            resultData.setCode(Code.SUCCESS);
            resultData.setMsg("添加成功");
        } catch (RuntimeException e) {
            resultData.setCode(Code.FALISE);
            resultData.setMsg("添加失败");
            e.printStackTrace();
        }
        System.out.println("=================>执行insertRole方法");
        return resultData;
    }
    //更新角色名和角色的权限
    public ResultData updateRole(String roleName, List<Integer> permissionIdList,Integer roleId) {
        ResultData<Object> resultData = new ResultData<>();
        Role role = new Role();
        role.setRolename(roleName);
        role.setId(roleId);
        Role begin_role = roleMapper.GetRoleBy(null,roleId);
        System.out.println(roleName);
        System.out.println(begin_role.getRolename());
        try {
            if(!begin_role.getRolename().equals(roleName)){
                roleMapper.updateRole(role);
                System.out.println("修改的角色ID" + role.getId());
            }
            rolePermissionMapper.deleteRolePermissio(roleId);
            for (int permissionId : permissionIdList) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(role.getId());
                rolePermission.setPermissionId(permissionId);
                rolePermissionMapper.insertRolePermissio(rolePermission);
            }
            resultData.setCode(Code.SUCCESS);
            resultData.setMsg("添加成功");
        } catch (RuntimeException e) {
            resultData.setCode(Code.FALISE);
            resultData.setMsg("添加失败");
            e.printStackTrace();
        }
        System.out.println("=================>执行updateRole方法");
        return resultData;
    }

    public ResultData deleteRole(int roleId) {
        ResultData<Object> resultData = new ResultData<>();
        try {
            roleMapper.deleteRole(roleId);
            resultData.setCode(Code.SUCCESS);
            resultData.setMsg("删除成功");
        }catch (RuntimeException e) {
            resultData.setCode(Code.FALISE);
            resultData.setMsg("添加失败");
            e.printStackTrace();
        }
        System.out.println("=================>执行deleteRole方法");
        return resultData;
    }

    public ResultData showAllRole(){
        ResultData resultData = new ResultData();
        List<Role> roles = roleMapper.GetAllRole();
        resultData.setCode(Code.SUCCESS);
        resultData.setData(roles);
        System.out.println("=================>执行showAllRole方法");
        return resultData;
    }

}
