package com.Ambition.service;

import com.Ambition.dto.ResultData;

import java.util.Date;

public interface OrderService {
    //查询所有订单
    ResultData getAllOrders();

    //添加订单
    ResultData addOrders(int id, String username, String goodsName,int goodsAmount, String time);

    //修改订单
    ResultData updateOrder(int id, String username, String goodsName,int goodsAmount, String time);

    //删除订单
    ResultData deleteOrder(int id);

//    //查询指定订单
//    ResultData getOrdersBy(int id, String username, String goodsName,int goodsAmount, String time);

    //模糊查询
    ResultData getOrdersLike(Integer id, String username, String goodsName);
}
