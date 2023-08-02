package com.Ambition.controller;

import com.Ambition.dto.ResultData;
import com.Ambition.service.IndexService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "首页")
@RestController
public class IndexController {

    @Resource
    private IndexService indexService;

    @GetMapping("/index/show")
    public ResultData index(String userCode){
        return indexService.getIndex(userCode);
    }
}
