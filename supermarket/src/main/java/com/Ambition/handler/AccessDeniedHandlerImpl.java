package com.Ambition.handler;

import com.Ambition.Utils.WebUtils;
import com.Ambition.dto.ResultData;
import com.alibaba.fastjson.JSON;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResultData result = new ResultData(403, "权限不足",null);
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);

    }
}
