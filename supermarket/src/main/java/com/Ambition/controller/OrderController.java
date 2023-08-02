package com.Ambition.controller;

import com.Ambition.Utils.Constant;
import com.Ambition.dto.ResultData;
import com.Ambition.service.OrderService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "订单管理")
@RestController
public class OrderController {
    @Resource
    private OrderService orderService;

    @ApiOperation("显示订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "pageNo", value = "页数", required = true, dataType = "Integer", dataTypeClass = Integer.class),

    })
    @PostMapping("/order")
    public ResultData showOrders(@RequestParam(defaultValue = "1") int pageNo) {
        PageHelper.startPage(pageNo, Constant.LIMIT);
        return orderService.getAllOrders();
    }

    @ApiOperation("增加订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "id", value = "订单号", required = true, dataType = "Integer", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "goodsName", value = "商品描述", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "goodsAmount", value = "商品数量", required = true, dataType = "Integer", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "time", value = "购买日期", required = true, dataType = "Date", dataTypeClass = Date.class),
    })
    @GetMapping("/order/add")
    public ResultData addOrders(int id, String username, String goodsName,int goodsAmount, String time) {
        return orderService.addOrders(id,username,goodsName,goodsAmount,time);
    }

    @ApiOperation("删除订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "id", value = "订单号", required = true, dataType = "Integer", dataTypeClass = Integer.class)
    })
    @GetMapping("/order/delete")
    public ResultData deleteOrder(@ApiParam("订单号")@RequestParam int id) {
        return orderService.deleteOrder(id);
    }

    @ApiOperation("修改订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "id", value = "订单号", required = true, dataType = "Integer", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "goodsName", value = "商品描述", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "goodsAmount", value = "商品数量", required = true, dataType = "Integer", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "time", value = "购买日期", required = true, dataType = "Date", dataTypeClass = Date.class),
    })
    @GetMapping("/order/update")
    public ResultData updateOrders(int id, String username, String goodsName,int goodsAmount, String time) {
        return orderService.updateOrder(id,username,goodsName,goodsAmount,time);
    }

    @ApiOperation("模糊查询订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "id", value = "订单号", required = true, dataType = "Integer", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "goodsName", value = "商品描述", required = true, dataType = "String", dataTypeClass = String.class),
    })
    @GetMapping("/order/like")
    public ResultData getOrdersLike(Integer id, String username, String goodsName){
        return orderService.getOrdersLike(id,username,goodsName);
    }

}
