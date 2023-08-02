package com.Ambition.service.impl;


import com.Ambition.Utils.Code;
import com.Ambition.Utils.JwtUtil;
import com.Ambition.Utils.RedisCache;
import com.Ambition.dto.ResultData;
import com.Ambition.dto.UserLogin;
import com.Ambition.mapper.UserMapper;
import com.Ambition.pojo.User;
import com.Ambition.service.LoginServcie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginServcie {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Resource
    private UserMapper userMapper;

    public ResultData login(User user) {
        //进行用户认证。获取AuthenticationManager authenticate
        //获取认证对象
        System.out.println(user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserCode(),user.getPassword());
        //认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //认证失败
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //认证成功，生成token
        //获取用户信息（getPrincipal()）
        //使用userid生成token
        UserLogin loginUser = (UserLogin) authenticate.getPrincipal();
        int userId = loginUser.getUser().getId();
        String token = JwtUtil.createJWT(String.valueOf(userId));
        System.out.println(token);
        ////authenticate存入redis
        redisCache.setCacheObject("user"+userId,loginUser,60, TimeUnit.MINUTES);
        //返回
        ResultData resultData = new ResultData();
        resultData.setCode(Code.SUCCESS);
        resultData.setMsg("登录成功");
        HashMap<String, String> Map = new HashMap<>();
        Map.put("token",token);
        resultData.setData(Map);
        return resultData;
    }

    public ResultData logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserLogin loginUser = (UserLogin) authentication.getPrincipal();
        int userid = loginUser.getUser().getId();
        redisCache.deleteObject("user"+userid);
        ResultData resultData = new ResultData();
        resultData.setCode(Code.SUCCESS);
        resultData.setMsg("退出成功");
        return resultData;
    }
}
