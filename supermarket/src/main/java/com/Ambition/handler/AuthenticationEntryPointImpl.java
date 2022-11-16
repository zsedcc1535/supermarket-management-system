package com.Ambition.handler;


import com.Ambition.Utils.WebUtils;
import com.Ambition.dto.ResultData;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//自定义认证失败返回
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //401认证失败
        ResultData result = new ResultData(401, "认证失败请重新登录",null);
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);
    }
}
