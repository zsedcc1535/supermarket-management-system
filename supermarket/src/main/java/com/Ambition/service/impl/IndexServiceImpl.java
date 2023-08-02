package com.Ambition.service.impl;

import com.Ambition.Utils.Code;
import com.Ambition.Utils.RedisCache;
import com.Ambition.dto.ResultData;
import com.Ambition.dto.UserLogin;
import com.Ambition.mapper.GoodsMapper;
import com.Ambition.mapper.OrderMapper;
import com.Ambition.mapper.RoleMapper;
import com.Ambition.mapper.UserMapper;
import com.Ambition.pojo.Role;
import com.Ambition.pojo.User;
import com.Ambition.service.IndexService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RedisCache redisCache;

    @Resource
    public RedisTemplate redisTemplate;

    public ResultData getIndex(String userCode){
        ResultData resultData = new ResultData();
        User user = userMapper.GetUserBy(null, userCode, null);
        Role role = roleMapper.GetRoleBy(null, user.getRoleId());
        Set keys = redisTemplate.keys("user*");
        ArrayList<Object> objects = new ArrayList<>();
        for (Object key : keys) {
            UserLogin cacheObject = redisCache.getCacheObject(String.valueOf(key));
            User user1 = userMapper.GetUserBy(cacheObject.getUser().getId(), null, null);
            Role role1 = roleMapper.GetRoleBy(null, user1.getRoleId());
            user1.setRole(role1);
            objects.add(user1);
        }
        assert keys != null;
        int size = keys.size();
        HashMap<String, Object> map = new HashMap<>();
        map.put("online",size);
        map.put("userNumber",userMapper.CountUser());
        map.put("goodsNumber",goodsMapper.CountGoods());
        map.put("orderNumber",orderMapper.CountOrder());
        map.put("username",user.getUserName());
        map.put("onlineUser",objects);
        map.put("role",role.getRolename());
        resultData.setCode(Code.SUCCESS);
        resultData.setMsg("查询成功");
        resultData.setData(map);
        return resultData;
    }
}
