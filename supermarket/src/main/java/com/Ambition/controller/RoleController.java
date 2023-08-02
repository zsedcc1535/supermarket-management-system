package com.Ambition.controller;

import com.Ambition.Utils.Constant;
import com.Ambition.dto.ResultData;
import com.Ambition.service.PermissionService;
import com.Ambition.service.RoleService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@Api(tags = "角色")
@RestController
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @ApiOperation("获取所有角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "pageNo", value = "页数", required = true, dataType = "Integer", dataTypeClass = Integer.class),
    })
    @GetMapping("/role/showRole")
    public ResultData show(@RequestParam(required = false, defaultValue = "1") int pageNo){
    //    System.out.println(roleService.GetAllRole());
        PageHelper.startPage(pageNo, Constant.LIMIT);
        return roleService.GetAllRole();
    }

    @ApiOperation("获取所有角色和对应权限")
    @GetMapping("/role/showAllRole")
    public ResultData showAllRole(){
        return roleService.showAllRole();
    }

    @ApiOperation("添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "data", value = "json对象{permissionIdList:[],roleName:}", required = true),
    })
    @PostMapping("/role/add")
    public ResultData addRole(@RequestBody Map<String, Object> data){
        ArrayList<Integer> permissionIdList = (ArrayList<Integer>)data.get("permissionIdList");
        String roleName = (String)data.get("roleName");
        String salary = (String) data.get("salary");
        ResultData resultData = roleService.insertRole(roleName, permissionIdList, Integer.valueOf(salary));
        return resultData;
    }

    @ApiOperation("修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "data", value = "json对象{permissionIdList:[],roleId:}", required = true),
    })
    @PostMapping("/role/update")
    public ResultData updateRole(@RequestBody Map<String, Object> data){
        System.out.println(data);
        ArrayList<Integer> permissionIdList = (ArrayList<Integer>)data.get("permissionIdList");
        String roleName = (String)data.get("rolename");
        int roleId = (int)data.get("roleId");
        ResultData resultData = roleService.updateRole(roleName, permissionIdList, roleId);
        return resultData;
    }

    @ApiOperation("删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "Integer",dataTypeClass = Integer.class),
    })
    @GetMapping("/role/delete")
    public ResultData removeRole(int roleId){
        return roleService.deleteRole(roleId);
    }
}
