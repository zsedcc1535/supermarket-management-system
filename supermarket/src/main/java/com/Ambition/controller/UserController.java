package com.Ambition.controller;

import com.Ambition.Utils.Code;
import com.Ambition.Utils.Constant;
import com.Ambition.dto.ResultData;
import com.Ambition.mapper.UserMapper;
import com.Ambition.pojo.User;
import com.Ambition.service.UserService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = "用户")
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @ApiOperation("显示员工列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "pageNo", value = "页数", required = true, dataType = "Integer", dataTypeClass = Integer.class)
    })
    @GetMapping("/user/show")
    public ResultData show(@RequestParam(required = false, defaultValue = "1") Integer pageNo){
        //  System.out.println(userService.GetAllUser());
        PageHelper.startPage(pageNo, 10);
        return userService.GetAllUser();
    }

    @ApiOperation("单个删除员工")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "value", value = "日志内容", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "pageNo", value = "页数", required = true, dataType = "Integer", dataTypeClass = Integer.class)
    })
    @PostMapping("/user/delete")
    public ResultData removeUser(@RequestParam Integer userId, @RequestParam Integer pageNo,@RequestParam String value) {
        User user = userMapper.GetUserBy(userId, null, null);
        ResultData resultData1 = userService.deleteUser(userId);
        if(user.getEmail() != null){
            resultData1.setCode(Code.SUCCESS);
            resultData1.setMsg("发送成功");
            userService.Email(value,userId,user.getEmail());
        }
        else{
            resultData1.setCode(Code.FALISE);
            resultData1.setMsg("发送失败");
        }
        PageHelper.startPage(pageNo, Constant.LIMIT);
        ResultData resultData = userService.GetAllUser();
        resultData1.setData(resultData.getData());
        return resultData1;
    }

    @ApiOperation("添加员工")
    @PostMapping("/user/add")
    public ResultData insertUser(@RequestBody User user) {
        return userService.insertUser(user);
    }

    @PostMapping("/user/update")
    public ResultData updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @ApiOperation("条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "data", value = "json对象{username:,roleId:,pageNo:}两个属性可以只传一个", required = true)
    })
    @PostMapping("/user/search")
    public ResultData searchUserByCondition(@RequestBody Map<String, Object> data) {
        String userName = (String) data.get("userName");
        Integer roleId = (Integer) data.get("roleId");
        User user = new User();
        if (userName != null){
            user.setUserName(userName);
        }
        if (roleId != null){
            user.setRoleId(roleId);
        }
        System.out.println(user);
        Integer pageNo = (Integer) data.get("pageNo");
        if (pageNo == null) {
            pageNo = 1;
        }
        PageHelper.startPage(pageNo, Constant.LIMIT);
        return userService.searchUser(user);
    }

    @ApiOperation("批量删除")
    @PostMapping("/user/beachDelete")
    public ResultData beachRemoveUser(@RequestBody Map<String, Object> map) {
        List<Integer> userIdlist = (ArrayList<Integer>) map.get("userIdlist");
        System.out.println(userIdlist);
        int pageNo = (int) map.get("pageNo");
        ResultData resultData1 = userService.deleteUserList(userIdlist);
        PageHelper.startPage(pageNo, Constant.LIMIT);
        ResultData resultData = userService.GetAllUser();
        resultData1.setData(resultData.getData());
        return resultData;
    }
}
