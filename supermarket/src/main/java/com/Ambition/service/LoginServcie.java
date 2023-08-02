package com.Ambition.service;


import com.Ambition.dto.ResultData;
import com.Ambition.pojo.User;

public interface LoginServcie {

    ResultData login(User user);

    ResultData logout();
}
