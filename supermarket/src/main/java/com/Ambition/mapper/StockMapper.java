package com.Ambition.mapper;

import com.Ambition.pojo.Stock;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockMapper {
    //获取全部权库存
    List<Stock> getAllStock();
    //查询库存
    List<Stock> searchStock(Integer goodsId,String goodsName);
    //增加库存
    void addStock(Integer goodsId,Integer goodsAmount,Integer standardAmount);
    //删除库存
    void deleteStock(Integer goodsId);
    //查询库存不足
    List<Stock> underStock();
    //修改库存
    void updateStock(Stock stock);
    //按照类型统计数量
    List<Map<String,String>> getStockStatistics();
}
