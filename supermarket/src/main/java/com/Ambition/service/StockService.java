package com.Ambition.service;

import com.Ambition.dto.ResultData;

import java.util.Map;

public interface StockService {
    //全部库存
    ResultData getAllStock();
    //查询库存
    ResultData searchStock(String goodsName);
    //库存不足
    Map<String, Object> underStock();
    //根据类型查询库存
    ResultData getStockStatistics();
}
