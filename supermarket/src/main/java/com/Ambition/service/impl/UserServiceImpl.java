package com.Ambition.service.impl;

import com.Ambition.Utils.Code;
import com.Ambition.dto.ResultData;
import com.Ambition.mapper.RoleMapper;
import com.Ambition.mapper.UserMapper;
import com.Ambition.pojo.Role;
import com.Ambition.pojo.User;
import com.Ambition.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl  implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;


    public User GetUserBy(String userCode, String password){
        User user = userMapper.GetUserBy(null,userCode, password);
        return user;
    }
    public ResultData GetAllUser(){
        ResultData<Map> resultData = new ResultData<>();
        List<User> users = userMapper.GetAllUser();
        //PageInfo<查询到的数据类型> pages = new PageInfo<>(list类型, 页面显示的导航条数);
        PageInfo<User> pages = new PageInfo<>(users);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", pages.getTotal());
        map.put("pages", pages.getPages());
        map.put("pageNum", pages.getPageNum());
        map.put("userList", pages.getList());
        resultData.setCode(200);
        resultData.setData(map);
        System.out.println("=================>执行GetAllUser方法");
        return resultData;
    }

    public ResultData deleteUser(Integer id){
        ResultData resultData = new ResultData();
        userMapper.deleteUser(id);
        resultData.setCode(Code.SUCCESS);
        resultData.setMsg("删除成功");
        System.out.println("=================>执行deleteUser方法");
        return resultData;
    }

    public ResultData insertUser(User user) {
        ResultData resultData = new ResultData();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        userMapper.addUser(user);
        resultData.setCode(Code.SUCCESS);
        resultData.setMsg("添加成功");
        System.out.println("=================>执行insertUser方法");
        return resultData;
    }

    public ResultData updateUser(User user) {
        ResultData resultData = new ResultData();
        User user1 = userMapper.GetUserBy(user.getId(),null, null);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(!user.getPassword().equals(user.getPassword())){
            if(user.getPassword()!= null && !user.getPassword().equals("")){
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
        }
        userMapper.updateUser(user);
        resultData.setCode(Code.SUCCESS);
        resultData.setMsg("修改成功");
        System.out.println("=================>执行updateUser方法");
        return resultData;
    }

    public ResultData searchUser(User user) {
        ResultData<Object> resultData = new ResultData<>();
        List<User> userList = null;
        if (user.getUserName() != null) {
            userList = userMapper.GetUserLike("%" + user.getUserName() + "%", null);
        }
        if (user.getRoleId() != null) {
            userList = userMapper.GetUserLike(null,user.getRoleId());
        }
        if(!userList.isEmpty()) {
            for (User user1 : userList) {
                Role role = roleMapper.GetRoleBy(null, user1.getRoleId());
                user1.setRole(role);
            }
        }
        System.out.println(userList);
        PageInfo<User> pages = new PageInfo<>(userList);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", pages.getTotal());
        map.put("pages", pages.getPages());
        map.put("pageNum", pages.getPageNum());
        map.put("userList", pages.getList());
        resultData.setCode(Code.SUCCESS);
        resultData.setData(map);
        return resultData;
    }

    public ResultData deleteUserList(List<Integer> userIdlist) {
        ResultData resultData = new ResultData();
        int number = 0;
        for (Integer integer : userIdlist) {
            number++;
            userMapper.deleteUser(integer);
        }
        resultData.setCode(Code.SUCCESS);
        resultData.setMsg("成功删除" + number + "条数据");
        return resultData;
    }
}
