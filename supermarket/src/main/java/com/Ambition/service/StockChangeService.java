package com.Ambition.service;

import com.Ambition.dto.ResultData;
import com.Ambition.pojo.StockChange;

import java.text.ParseException;
import java.util.Map;

public interface StockChangeService {
    //搜索出/入库记录
    ResultData check(int type) throws ParseException;
    //出库查询
    ResultData checkSearch(int type,String goodsName);
    //添加出库记录
    ResultData checkAdd(int type, Map<String, String> map, int id) throws Exception;
    //报表统计查询出入库
    Map<String, Object> getStockChangeBy(int typeCode, Integer dateCode);
    //统计一周内的出库总量与入库总量
    Map<String, Integer> getCheckStatistics();
    //删除出入库消息
    ResultData checkDelete(int id,int type);
    //修改出入库信息
    ResultData checkChangeUpdate(StockChange stockChange);
}
