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
    //修改出入库信息
    void updateStockChange(StockChange stockChange);
    //删除出入库信息
    void deleteStockChange(Integer id,Integer type);
    //根据id查询
    StockChange getStockChangeById(Integer id);
}
