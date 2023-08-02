package com.Ambition.controller;

import com.Ambition.Utils.Code;
import com.Ambition.Utils.Constant;
import com.Ambition.dto.ResultData;
import com.Ambition.pojo.StockChange;
import com.Ambition.pojo.User;
import com.Ambition.service.StockChangeService;
import com.Ambition.service.impl.StockChangeServiceImpl;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Map;

@Api(tags = "库存管理")
@RestController
public class StockChangeController {

    @Resource
    private StockChangeService stockChangeService;

    @ApiOperation("出库记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "Integer", dataTypeClass = Integer.class)
    })
    @PostMapping("/checkout")
    public ResultData checkout(@RequestParam(defaultValue = "1") int page) throws ParseException {
        PageHelper.startPage(page, Constant.LIMIT);
        return stockChangeService.check(0);
    }


    @ApiOperation("入库记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "Integer", dataTypeClass = Integer.class)
    })
    @PostMapping("/checkin")
    public ResultData checkin(@RequestParam(defaultValue = "1") int page) throws ParseException {
        PageHelper.startPage(page, Constant.LIMIT);
        return stockChangeService.check(1);
    }


    @ApiOperation("搜索出库记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "goodsName", value = "商品名", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "Integer", dataTypeClass = Integer.class)
    })
    @PostMapping("/checkoutSearch")
    public ResultData checkoutSearch(String goodsName,int page){
        PageHelper.startPage(page, Constant.LIMIT);
        return stockChangeService.checkSearch(0,goodsName);
    }


    @ApiOperation("搜索入库记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "goodsName", value = "商品名", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "Integer", dataTypeClass = Integer.class)
    })
    @PostMapping("/checkinSearch")
    public ResultData checkinSearch(String goodsName,int page){
        PageHelper.startPage(page, Constant.LIMIT);
        return stockChangeService.checkSearch(1,goodsName);
    }

    @ApiOperation("商品出库")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "map",value = "json对象",required = true)
    })
    @PostMapping("/checkoutAdd")
    public ResultData checkoutAdd(@RequestBody Map<String,String> map, HttpSession httpSession) throws Exception {
        User user = (User) httpSession.getAttribute("user");
        ResultData resultData = new ResultData();
        if (user == null) {
            resultData.setCode(Code.FALISE);
            resultData.setMsg("未登录");
            return resultData;
        }
        int id = user.getId();
        return  stockChangeService.checkAdd(0,map,id);
    }

    @ApiOperation("商品入库")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "map",value = "json对象",required = true)
    })
    @PostMapping("/checkinAdd")
    public ResultData checkinAdd(@RequestBody Map<String,String> map, HttpSession httpSession) throws Exception {
        User user = (User) httpSession.getAttribute("user");
        ResultData resultData = new ResultData();
        if (user == null) {
            resultData.setCode(Code.FALISE);
            resultData.setMsg("未登录");
            return resultData;
        }
        int id = user.getId();
        return  stockChangeService.checkAdd(1,map,id);
    }

    @ApiOperation("删除入库记录")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer", dataTypeClass = Integer.class),
    })
    @PostMapping("/checkinDelete")
    public ResultData checkinDelete(int id){
        return stockChangeService.checkDelete(id,1);
    }

    @ApiOperation("删除出库记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer", dataTypeClass = Integer.class),
    })
    @PostMapping("/checkoutDelete")
    public ResultData checkoutDelete(int id){
        return stockChangeService.checkDelete(id,0);
    }

    @ApiOperation("修改出库记录")
    @PostMapping("/checkoutEdit")
    public ResultData checkoutEdit(@RequestBody StockChange stockChange){
        System.out.println(stockChange);
        return stockChangeService.checkChangeUpdate(stockChange);
    }

    @ApiOperation("修改出库记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer", dataTypeClass = Integer.class),
    })
    @PostMapping("/checkinEdit")
    public ResultData checkinEdit(@RequestBody StockChange stockChange){
        return stockChangeService.checkChangeUpdate(stockChange);
    }
}
