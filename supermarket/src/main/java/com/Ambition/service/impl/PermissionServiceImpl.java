package com.Ambition.service.impl;

import com.Ambition.Utils.Code;
import com.Ambition.dto.ResultData;
import com.Ambition.mapper.PermissionMapper;
import com.Ambition.pojo.Permission;
import com.Ambition.service.PermissionService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RedisTemplate redisTemplate;

    public ResultData GetAllPermission(){
        Boolean AllPermission = redisTemplate.hasKey("AllPermission");
        ResultData AllPermission1 = null;
        if(AllPermission.equals(true)){
            AllPermission1 = (ResultData)redisTemplate.opsForValue().get("AllPermission");
        }
        //缓存为空或者修改过
        if(AllPermission1 == null) {
            ResultData resultData = new ResultData();
            List<Permission> permissions = permissionMapper.GetAllPermission();
            resultData.setCode(Code.SUCCESS);
            resultData.setData(permissions);
            redisTemplate.opsForValue().set("AllPermission",resultData);
            System.out.println("=================>执行GetAllPermission方法");
            return resultData;
        }
        else{
            System.out.println("=================>从redis中获取所有权限信息");
            return AllPermission1;
        }
    }
}
