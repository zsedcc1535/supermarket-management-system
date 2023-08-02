package com.Ambition.service.impl;

import com.Ambition.Utils.AppDateUtils;
import com.Ambition.Utils.Code;
import com.Ambition.dto.ResultData;
import com.Ambition.mapper.GoodsMapper;
import com.Ambition.mapper.OrderMapper;
import com.Ambition.mapper.StockMapper;
import com.Ambition.pojo.*;
import com.Ambition.service.OrderService;
import com.github.pagehelper.PageInfo;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderServiceImpl implements  OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private StockMapper stockMapper;

    public ResultData getAllOrders(){
        ResultData<Object> resultData = new ResultData<>();
        List<Order> allOrders = orderMapper.getAllOrders();
        PageInfo<Order> pages = new PageInfo<>(allOrders);
        for (Order order : allOrders) {
            Date createTime = order.getTime();
            String formatTime = AppDateUtils.getFormatTime( "yyyy-MM-dd",createTime);
            order.setMyData(formatTime);
        }
        resultData.setCode(200);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", pages.getTotal());
        map.put("pages", pages.getPages());
        map.put("pagenum", pages.getPageNum());
        map.put("list", pages.getList());
        resultData.setData(map);
        System.out.println("=================>执行getAllOrders方法");
        return resultData;
    }

    public ResultData addOrders(int id, String username, String goodsName,int goodsAmount, String time){
        ResultData resultData = new ResultData();
        Date formatTime = null;
        try {
            formatTime = AppDateUtils.toDate(time);
            System.out.println(formatTime);
            Date date = new Date();
            if (formatTime.after(date)){
                resultData.setCode(Code.FALISE);
                resultData.setMsg("时间选择错误");
                return resultData;
            }
        } catch (Exception e) {
            resultData.setCode(Code.FALISE);
            resultData.setMsg("时间格式错误");
            e.printStackTrace();
            return resultData;
        }
        List<Order> ordersBy = orderMapper.getOrdersBy(id,null,null,null);
        if(!ordersBy.isEmpty()){
            resultData.setCode(Code.FALISE);
            resultData.setMsg("订单已经存在");
            return resultData;
        }
        else{
            List<Stock> stocks = stockMapper.searchStock(null, goodsName);
            List<Goods> goodsBy = goodsMapper.getGoodsBy(goodsName, null);
            int flag = 0;
            for (Goods goods : goodsBy) {
                if(flag == 1){
                    break;
                }
                if (goods.getState() == 1){
                    continue;
                }
                else{
                    List<Stock> stocks1 = stockMapper.searchStock(goods.getId(), null);
                    for (Stock stock : stocks) {
                        int amount = stock.getGoodsAmount();
                        if (goodsAmount > amount){
                            continue;
                        }
                        stock.setGoodsAmount(amount-goodsAmount);
                        stockMapper.updateStock(stock);
                        flag = 1;
                        break;
                    }
                }
            }
            if(flag == 0){
                    resultData.setCode(Code.FALISE);
                    resultData.setMsg("库存不够，等待补充货物");
            }
            else{
                resultData.setCode(Code.SUCCESS);
                resultData.setMsg("添加成功");
                Order order = new Order();
                order.setId(id);
                order.setUsername(username);
                order.setGoodsName(goodsName);
                order.setGoodsAmount(goodsAmount);
                order.setTime(formatTime);
                orderMapper.addOrders(order);
            }
        }
        System.out.println("=================>执行addOrders方法");
        return resultData;
    }

    public ResultData deleteOrder(int id){
        ResultData resultData = new ResultData();
        List<Order> ordersBy = orderMapper.getOrdersBy(id, null, null, null);
        System.out.println(ordersBy);
        List<Goods> goodsBy = goodsMapper.getGoodsBy(ordersBy.get(0).getGoodsName(), null);
        List<Stock> stocks = null;
        for (Goods goods : goodsBy) {
            stocks = stockMapper.searchStock(goods.getId(), null);
            break;
        }
        Stock stock = stocks.get(0);
        stock.setGoodsAmount(stock.getGoodsAmount()+ordersBy.get(0).getGoodsAmount());
        stockMapper.updateStock(stock);

        orderMapper.deleteOrder(id);
        resultData.setCode(Code.SUCCESS);
        resultData.setMsg("订单删除成功");
        System.out.println("=================>执行deleteOrder方法");
        return resultData;
    }

    public ResultData updateOrder(int id, String username, String goodsName,int goodsAmount, String time){
        ResultData resultData = new ResultData();
        Date formatTime = null;
        try {
            formatTime = AppDateUtils.toDate(time);
            Date date = new Date();
            if (formatTime.after(date)){
                resultData.setCode(Code.FALISE);
                resultData.setMsg("时间选择错误");
                return resultData;
            }
        } catch (Exception e) {
            resultData.setCode(Code.FALISE);
            resultData.setMsg("时间格式错误");
            e.printStackTrace();
            return resultData;
        }
        List<Order> ordersBy = orderMapper.getOrdersBy(id, null, null, null);
        Order order1 = ordersBy.get(0);
        int beforeAmount =order1.getGoodsAmount();
        List<Goods> goodsBy = goodsMapper.getGoodsBy(goodsName, null);
        if(goodsBy.isEmpty()){
            resultData.setCode(Code.FALISE);
            resultData.setMsg("不存在该商品");
            return resultData;
        }
        if(!order1.getGoodsName().equals(goodsName)){
            List<Goods> goodsBy1 = goodsMapper.getGoodsBy(order1.getGoodsName(), null);
            int flag2 = 0;
            for (Goods goods : goodsBy1) {
                if(flag2 == 1){
                    break;
                }
                List<Stock> stocks = stockMapper.searchStock(goods.getId(), null);
                for (Stock stock : stocks) {
                    stock.setGoodsAmount(stock.getGoodsAmount()+order1.getGoodsAmount());
                    stockMapper.updateStock(stock);
                    flag2 = 1;
                    break;
                }
            }
            beforeAmount = 0;
        }
        Stock stock =null;
        int flag = 0;
        for (Goods goods : goodsBy) {
            List<Stock> stocks = stockMapper.searchStock(goods.getId(), null);
            for (Stock stock1 : stocks) {
                stock = stock1;
                flag = 1;
                break;
            }
        }
        if(flag == 0){
            resultData.setCode(Code.FALISE);
            resultData.setMsg("该商品已下架");
            return resultData;
        }
        if ( stock.getGoodsAmount()-goodsAmount + beforeAmount  < 0) {
            resultData.setCode(Code.FALISE);
            resultData.setMsg("库存不够");
            return resultData;
        }
        else {
            stock.setGoodsAmount(stock.getGoodsAmount() + beforeAmount - goodsAmount);
            stockMapper.updateStock(stock);
        }
        Order order = new Order();
        order.setId(id);
        order.setUsername(username);
        order.setGoodsName(goodsName);
        order.setGoodsAmount(goodsAmount);
        order.setTime(formatTime);
        orderMapper.updateOrder(order);
        resultData.setCode(Code.SUCCESS);
        resultData.setMsg("订单修改成功");
        System.out.println("=================>执行updateOrder方法");
        return resultData;
    }

    public ResultData getOrdersLike(Integer id,String username, String goodsName){
        ResultData<Object> resultData = new ResultData<>();
        List<Order> orderList = null;
        if(username!=null){
            username = "%"+username+"%";
        }
        if(goodsName!=null){
            goodsName = "%"+goodsName+"%";
        }
        if (id != null){
            orderList = orderMapper.getOrdersBy(id,null,null,null);
        }

        else{
            orderList = orderMapper.getOrderslike(null,username,goodsName );
        }
        PageInfo<Order> pages = new PageInfo<>(orderList);
        for (Order order : orderList) {
            Date createTime = order.getTime();
            String formatTime = AppDateUtils.getFormatTime( "yyyy-MM-dd HH:mm:ss",createTime);
            order.setMyData(formatTime);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", pages.getTotal());
        map.put("pages", pages.getPages());
        map.put("pageNum", pages.getPageNum());
        map.put("orderList", pages.getList());
        resultData.setMsg("查询成功");
        resultData.setCode(Code.SUCCESS);
        resultData.setData(map);
        return resultData;
    }

}
