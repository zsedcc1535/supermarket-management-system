package com.Ambition.controller;

import com.Ambition.Utils.Constant;
import com.Ambition.service.GoodsService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@Api(tags = "即将过期")
@RestController
public class expirationController {

    @Resource
    private GoodsService goodsService;

    @ApiOperation("即将过期")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "pageNo", value = "页数", required = true, dataType = "Integer", dataTypeClass = Integer.class)
    })
    @GetMapping("/change")
    public Map change(@RequestParam(defaultValue = "1")int pageNo){
        PageHelper.startPage(pageNo, Constant.LIMIT);
        return goodsService.change();
    }
}
