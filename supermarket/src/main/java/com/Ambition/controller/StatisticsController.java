package com.Ambition.controller;


import com.Ambition.Utils.Code;
import com.Ambition.dto.ResultData;
import com.Ambition.service.StockChangeService;
import com.Ambition.service.StockService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;


@Api(tags = "报表统计接口")
@RestController
public class StatisticsController {

    @Resource
    StockChangeService stockChangeService;

    @Resource
    StockService stockService;

    private static final int DEFAULT_COUNT = 4;

    @ApiOperation("按时间分页查询出库记录或入库记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "登录令牌", dataType = "String", dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name = "typeCode", value = "出入库类型状态码（不限 0，入库 1 ，出库 2）", required = true, paramType = "query", dataType = "Integer", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "dateCode", value = "时间类型状态码（不限 0，日 1，周 2，月 3）", required = true, paramType = "query", dataType = "Integer", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "start", value = "获取数据的页码", required = true, paramType = "query", dataType = "Integer", dataTypeClass = Integer.class)
    })
    @GetMapping("/statistics/check/list")
    public ResultData getStockChangeBy(@RequestParam Integer typeCode, @RequestParam Integer dateCode, @RequestParam Integer start) {
        ResultData resultData = new ResultData();
        System.out.println("=======================>");
        //请求数据完整进行查询
        if (start != null && typeCode != null && dateCode != null) {
            PageHelper.startPage(start, DEFAULT_COUNT);
            //查询结果
            Map<String, Object> map = stockChangeService.getStockChangeBy(typeCode, dateCode);
            resultData.setCode(Code.SUCCESS);
            resultData.setMsg("查询成功！");
            resultData.setData(map);
            return resultData;
        } else {
            //请求数据不完整返回请求错误
            resultData.setCode(Code.FALISE);
            resultData.setMsg("请求数据不完整！");
            resultData.setData(null);
            return resultData;
        }
    }

    @ApiOperation("按类型统计商品库存")
    @GetMapping("/statistics/stock")
    public ResultData getStockStatistics() {
        return stockService.getStockStatistics();
    }

    @ApiOperation("统计一周内的出库总量与入库总量")
    @GetMapping("/statistics/check")
    public ResultData getCheckStatistics() {
        ResultData resultData = new ResultData();
        resultData.setCode(Code.SUCCESS);
        resultData.setMsg("查询成功！");
        resultData.setData(stockChangeService.getCheckStatistics());
        return resultData;
    }

}
