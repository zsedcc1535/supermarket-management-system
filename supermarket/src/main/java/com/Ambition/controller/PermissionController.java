package com.Ambition.controller;

import com.Ambition.dto.ResultData;
import com.Ambition.service.PermissionService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "权限")
@RestController
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @ApiOperation("获取所有权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "pageNo", value = "页数", required = true, dataType = "Integer", dataTypeClass = Integer.class),
    })
    @GetMapping("/permission/show")
    public ResultData show(@RequestParam(required = false, defaultValue = "1") int pageNo){
        return permissionService.GetAllPermission();
    }

}
