package com.Ambition.controller;

import com.Ambition.Utils.Constant;
import com.Ambition.dto.ResultData;
import com.Ambition.mapper.GoodsMapper;
import com.Ambition.pojo.Goods;
import com.Ambition.service.GoodsService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "商品")
@RestController
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private GoodsMapper goodsMapper;

    @ApiOperation("显示商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "pageNo", value = "页数", required = true, dataType = "Integer", dataTypeClass = Integer.class),

    })
    @PostMapping("/goods")
    public ResultData showGoods(@RequestParam(defaultValue = "1") int pageNo) {
        PageHelper.startPage(pageNo, Constant.LIMIT);
        return goodsService.GetAllGoods();
    }

    @ApiOperation("增加商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "goodsName", value = "商品名", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "name", value = "类型名", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "goodsDesc", value = "商品描述", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "shelfLife", value = "保质期", required = true, dataType = "Integer", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "productTime", value = "生产日期", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "price", value = "价格", required = true, dataType = "String", dataTypeClass = String.class)
    })
    @GetMapping("/goods/add")
    public ResultData addGoods(String goodsName, String name, String goodsDesc, Integer shelfLife, String productTime,String price) {
        return goodsService.addGoods(goodsName,name,goodsDesc,shelfLife,productTime,price);
    }

    @ApiOperation("删除商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "id", value = "商品id", required = true, dataType = "Integer", dataTypeClass = Integer.class)
    })
    @GetMapping("/goods/delete")
    public ResultData deleteGoods(@ApiParam("商品id")@RequestParam int id) {
        return goodsService.deleteGoods(id);
    }

    @ApiOperation("修改商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "id", value = "商品id", required = true, dataType = "Integer", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "goodsName", value = "商品名", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "name", value = "类型名", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "goodsDesc", value = "商品描述", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "shelfLife", value = "保质期", required = true, dataType = "Integer", dataTypeClass = Integer.class),
    })
    @GetMapping("/goods/update")
    public ResultData updateGoods(int id, String goodsName, String name, String goodsDesc, int shelfLife) {
        return goodsService.updateGoods(id, goodsName, name, goodsDesc, shelfLife);
    }

    @ApiOperation("修改商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "id", value = "商品id", required = true, dataType = "Integer", dataTypeClass = Integer.class),
    })
    @GetMapping("/goods/search")
    public ResultData searchGoods(int id) {
        Goods goodsById = goodsMapper.getGoodsById(id);
        ResultData resultData = new ResultData();
        resultData.setCode(200);
        resultData.setMsg("查询成功");
        resultData.setData(goodsById);
        return resultData;
    }
}
