package com.Ambition.controller;

import com.Ambition.Utils.Constant;
import com.Ambition.dto.ResultData;
import com.Ambition.service.StockService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;


@Api(tags = "库存管理")
@RestController
public class StockController {

    @Resource
    private StockService stockService;

    @ApiOperation("全部库存")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "Integer", dataTypeClass = Integer.class)
    })
    @PostMapping("/stock")
    public ResultData stock(@RequestParam(required = false, defaultValue = "1") int page){
        PageHelper.startPage(page, Constant.LIMIT);
        return stockService.getAllStock();
    }

    @ApiOperation("搜索库存")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "goodsName", value = "商品名", required = true, dataType = "String", dataTypeClass = String.class)
    })
    @PostMapping("/stock/search")
    public ResultData stockSearch(String goodsName){
        return stockService.searchStock("%"+goodsName+"%");
    }

    @ApiOperation("库存不足")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "pageNo", value = "页数", required = true, dataType = "Integer", dataTypeClass = Integer.class)
    })
    @GetMapping("/notify")
    public Map underStock(@RequestParam(defaultValue = "1")int pageNo){
        PageHelper.startPage(pageNo, Constant.LIMIT);
        return stockService.underStock();
    }
}
