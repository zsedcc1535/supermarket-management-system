package com.Ambition.service.impl;

import com.Ambition.Utils.AppDateUtils;
import com.Ambition.Utils.Code;
import com.Ambition.dto.ResultData;
import com.Ambition.mapper.GoodsMapper;
import com.Ambition.mapper.StockChangeMapper;

import com.Ambition.mapper.StockMapper;
import com.Ambition.pojo.Goods;
import com.Ambition.pojo.Stock;
import com.Ambition.pojo.StockChange;
import com.Ambition.service.StockChangeService;
import com.github.pagehelper.PageInfo;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockChangeServiceImpl implements StockChangeService {

    @Resource
    private StockChangeMapper stockChangeMapper;

    @Resource
    private StockMapper stockMapper;

    @Resource
    private GoodsMapper goodsMapper;

    public ResultData check(int type) throws ParseException {
        //查询所有的出/入库记录
        ResultData resultData = new ResultData();
        List<StockChange> allStockChange = stockChangeMapper.getStockChangeBy(type,null,null);
        PageInfo<StockChange> pages = new PageInfo<>(allStockChange);
        resultData.setCode(200);
        resultData.setMsg("查询成功");
        HashMap<String, Object> map = new HashMap<>();
        map.put("count", pages.getPageNum());
        map.put("stock",allStockChange);
        resultData.setData(map);
        System.out.println(resultData);
        return resultData;
    }

    public ResultData checkSearch(int type,String goodsName){
        System.out.println("=============================>"+"checkSearch执行");
        ResultData resultData = new ResultData();
        List<StockChange> stockChangeBy = stockChangeMapper.getStockChangeBy(type, "%" + goodsName + "%",null);
        PageInfo<StockChange> pages = new PageInfo<>(stockChangeBy);
        HashMap<String, Object> map = new HashMap<>();
        resultData.setCode(200);
        if(map != null) {
            resultData.setCode(Code.SUCCESS);
            resultData.setMsg("查询成功");
            map.put("count", pages.getPageNum());
            map.put("mapList",stockChangeBy);
            resultData.setData(map);
        }else {
            resultData.setCode(Code.FALISE);
            resultData.setMsg("没有该商品的出库记录");
        }
        System.out.println(resultData);
        return resultData;
    }

    public ResultData checkAdd(int type, Map<String, String> map, int id) throws Exception {
        ResultData resultData = new ResultData();
        Goods goods = new Goods();
        goods.setGoodsName(map.get("goodsName"));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String date = map.get("productTime");
        String date1 = map.get("checkoutTime");
        String date2 = map.get("checkinTime");
        String num = map.get("num");
        String s = AppDateUtils.MyDate(date);
        List<Goods> goodsList = goodsMapper.getGoodsBy(goods.getGoodsName(),s);
        System.out.println(goodsList);
        //出库
        if(type == 0){
            if(goodsList.isEmpty()){
                resultData.setCode(Code.FALISE);
                resultData.setMsg("没有该商品信息，需要添加");
                return resultData;
            }
            int flag = 0;
            for (Goods goods1 : goodsList) {
                List<Stock> stocks = stockMapper.searchStock(goods1.getId(), null);
                if(stocks.isEmpty()){
                    resultData.setCode(Code.FALISE);
                    resultData.setMsg("商品库存不足，需要添加");
                    return resultData;
                }
                Stock stock =stocks.get(0);
                if(stock.getGoodsAmount()>Integer.parseInt(num)){
                    stock.setGoodsAmount(stock.getGoodsAmount()-Integer.parseInt(num));
                    stockMapper.updateStock(stock);
                    goods = goods1;
                    flag = 1;
                    break;
                }
            }
            if(flag == 1){
                //插入一条商品的出入库时间
                StockChange stockChange = new StockChange();
                stockChange.setTime(AppDateUtils.toDate(date1));
                stockChange.setGoodsId(goods.getId());
                stockChange.setAmount(Integer.parseInt(num));
                stockChange.setType(type);
                stockChange.setUserId(id);
                stockChange.setState(0);
                stockChangeMapper.insertStockChange(stockChange);
            }
            else{
                resultData.setCode(Code.FALISE);
                resultData.setMsg("库存不足，需要修改");
                return resultData;
            }
        }
        //入库
        else{
            //商品存在 增加库存
            if(!goodsList.isEmpty()){
                Goods goods1 = goodsList.get(0);
                List<Stock> stocks = stockMapper.searchStock(goods1.getId(), null);
                if(stocks.isEmpty()){
                    stockMapper.addStock(goods1.getId(),Integer.parseInt(num),null);
                }
                else{
                    Stock stock = stocks.get(0);
                    stock.setGoodsAmount(stock.getGoodsAmount()+Integer.parseInt(map.get("num")));
                    stockMapper.updateStock(stock);
                }
            }
            //商品不存在 添加商品 添加新的库存记录
            else{
                Goods goods_1 = new Goods();
                goods_1.setGoodsName(map.get("goodsName"));
                goods_1.setProductTime(AppDateUtils.toDate(s));
                goodsMapper.addGoods(goods_1);
                goodsList = goodsMapper.getGoodsBy(map.get("goodsName"),s);
                stockMapper.addStock(goodsList.get(0).getId(),Integer.parseInt(num),null);
            }
            StockChange stockChange = new StockChange();
            System.out.println(date2);
            System.out.println(AppDateUtils.toDate(date2));
            stockChange.setTime(AppDateUtils.toDate(date2));
            goodsList = goodsMapper.getGoodsBy(map.get("goodsName"),s);
            stockChange.setGoodsId(goodsList.get(0).getId());
            stockChange.setAmount(Integer.parseInt(num));
            stockChange.setType(type);
            stockChange.setUserId(id);
            stockChange.setState(1);
            stockChangeMapper.insertStockChange(stockChange);
        }
        System.out.println("=============================>"+"checkAdd执行");
        return resultData;
    }

    public Map<String, Object> getStockChangeBy(int typeCode, Integer dateCode){
        if(dateCode == 0) dateCode = null;
        else if(dateCode == 1) dateCode = 1;
        else if(dateCode == 2) dateCode = 7;
        else if(dateCode == 3) dateCode = 30;
        if(typeCode == 2) typeCode = 0;
        else if(typeCode == 0) typeCode = 2;
        List<StockChange> stockChangeBy = stockChangeMapper.getStockChangeBy(typeCode, null, dateCode);
        if(typeCode == 0){
            for (StockChange stockChange : stockChangeBy) {
                stockChange.setOutInType("出库");
            }
        }
        else if(typeCode == 1){
            for (StockChange stockChange : stockChangeBy) {
                stockChange.setOutInType("入库");
            }
        }
        else if(typeCode == 2){
            for (StockChange stockChange : stockChangeBy) {
                stockChange.setOutInType("出入库");
            }
        }
        System.out.println(stockChangeBy);
        PageInfo<StockChange> pageInfo = new PageInfo<>(stockChangeBy, 3);
        List<StockChange> list = pageInfo.getList();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("pages", pageInfo.getPages());
        map.put("total",pageInfo.getTotal());
        map.put("start", pageInfo.getPageNum());
        return map;
    }
    public Map<String, Integer> getCheckStatistics(){
        Map<String,Integer> map=new HashMap<String, Integer>();
        map.put("checkin",stockChangeMapper.getCheckStatistics(1));
        map.put("checkout",stockChangeMapper.getCheckStatistics(0));
        return map;
    }
}
