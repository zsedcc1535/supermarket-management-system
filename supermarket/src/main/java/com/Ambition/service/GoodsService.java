package com.Ambition.service;

import com.Ambition.dto.ResultData;
import com.Ambition.pojo.Goods;

import java.util.Map;

public interface GoodsService {
    ResultData GetAllGoods();
    //添加商品
    ResultData addGoods(String goodsName, String name, String goodsDesc, int shelfLife, String productTime,String price);
    //删除商品
    ResultData deleteGoods(int id);
    //更改商品信息
    ResultData updateGoods(int id, String goodsName, String name, String goodsDesc, int shelfLife);
    //查询即将过期
    Map<String, Object> change();
    //模糊查询
}
