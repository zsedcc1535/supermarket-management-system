package com.Ambition.mapper;

import com.Ambition.pojo.StockChange;
import com.sun.java.swing.plaf.windows.WindowsTextAreaUI;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StockChangeMapper {
    List<StockChange> getStockChangeBy(Integer type,String goodsName,Integer dateCode);

    void insertStockChange(StockChange stockChange);

    Integer getCheckStatistics(Integer type);
}
