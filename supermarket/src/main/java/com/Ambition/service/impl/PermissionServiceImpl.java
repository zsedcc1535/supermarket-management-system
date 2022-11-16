package com.Ambition.service.impl;

import com.Ambition.Utils.Code;
import com.Ambition.dto.ResultData;
import com.Ambition.mapper.PermissionMapper;
import com.Ambition.pojo.Permission;
import com.Ambition.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    public ResultData GetAllPermission(){
        ResultData resultData = new ResultData();
        List<Permission> permissions = permissionMapper.GetAllPermission();
        resultData.setCode(Code.SUCCESS);
        resultData.setData(permissions);
        System.out.println("=================>执行GetAllPermission方法");
        return resultData;
    }
}
