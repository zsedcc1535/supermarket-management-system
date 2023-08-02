package com.Ambition.mapper;

import com.Ambition.dto.ResultData;
import com.Ambition.pojo.Expiration;
import com.Ambition.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {
    //查询全部商品和商品的种类
    List<Goods> getAllGoods();
    //添加商品
    void addGoods(Goods goods);

    //查询指定商品
    List<Goods> getGoodsBy(String goodsName, String productTime) ;

    //删除指定商品
    void deleteGoods(Integer id);
    //修改商品信息
    void updateGoods(Goods goods);
    //即将过期商品
    List<Expiration> expiration();
    //即将过期商品实施降价处理
    void discount();
    //对过期食品处理
    void overdue();

    int CountGoods();

    Goods getGoodsById(Integer id);
}
