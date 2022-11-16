package com.Ambition.controller;

import com.Ambition.config.SpringSecurityConfig;
import com.Ambition.dto.ResultData;
import com.Ambition.pojo.User;
import com.Ambition.service.LoginServcie;
import com.Ambition.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Api(tags = "登录登出")
@RestController
public class LoginController {

    @Resource
    private UserService userService;

    @Resource
    private LoginServcie loginServcie;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private SpringSecurityConfig springSecurityConfig;

    @ApiOperation("登录系统")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "String", dataTypeClass = String.class)
    })
    @PostMapping("/tologin")
    public ResultData login(HttpSession session, @RequestParam()String username, @RequestParam()String password){
        /*登录验证数据*/
        User user = new User();
        user.setUserCode(username);
        user.setPassword(password);
        ResultData login = loginServcie.login(user);
        if(login.getCode() == 200){
            User user1 = userService.GetUserBy(username, bCryptPasswordEncoder.encode(password));
            session.setAttribute("user",user1);
        }
        return login;
    }

    @ApiOperation("注销")
    @GetMapping("/toLoginOut")
    public ResultData logout(){
        return loginServcie.logout();
    }


    @GetMapping("/unLogin")
    public String unLogin(){
        return "未登录";
    }

}
