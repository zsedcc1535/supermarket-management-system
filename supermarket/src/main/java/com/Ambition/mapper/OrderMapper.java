package com.Ambition.mapper;

import com.Ambition.pojo.Goods;
import com.Ambition.pojo.Order;
import com.Ambition.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {

    //查寻所有订单
    List<Order> getAllOrders();

    //添加订单
    void addOrders(Order order);

    //查寻指定订单
    List<Order> getOrdersBy(Integer id, String username, String goodsName, Date time);

    //删除订单
    void deleteOrder(int id);

    //修改订单
    void updateOrder(Order order);

    //查寻指定订单
    List<Order> getOrderslike(Integer id,String username, String goodsName);

    //订单数量
    int CountOrder();

}
