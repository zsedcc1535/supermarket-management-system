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

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Resource
    private RedisTemplate redisTemplate;

    public ResultData check(int type) throws ParseException {
        //查询所有的出/入库记录
        ResultData resultData = new ResultData();
        List<StockChange> allStockChange = stockChangeMapper.getStockChangeBy(type,null,null);
        PageInfo<StockChange> pages = new PageInfo<>(allStockChange);
        resultData.setCode(200);
        resultData.setMsg("查询成功");
        HashMap<String, Object> map = new HashMap<>();
        map.put("count", pages.getTotal());
        for (StockChange stockChange : allStockChange) {
            Date createTime = stockChange.getTime();
            String formatTime = AppDateUtils.getFormatTime( "yyyy-MM-dd HH:mm:ss",createTime);
            stockChange.setDataTime(formatTime);
        }
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
        for (StockChange stockChange : stockChangeBy) {
            Date createTime = stockChange.getTime();
            String formatTime = AppDateUtils.getFormatTime( "yyyy-MM-dd HH:mm:ss",createTime);
            stockChange.setDataTime(formatTime);
        }
        if(map != null) {
            resultData.setCode(Code.SUCCESS);
            resultData.setMsg("查询成功");
            map.put("count", pages.getTotal());
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
        Date formatTime = null;
        try {
            formatTime = AppDateUtils.toDate(date);
            Date date3 = new Date();
            if (formatTime.after(date3)){
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
                redisTemplate.delete("stock");
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
                List<Goods> goodsName = goodsMapper.getGoodsBy(map.get("goodsName"), null);
                if(goodsName.isEmpty()){
                    resultData.setCode(Code.FALISE);
                    resultData.setMsg("商品名称错误");
                    return resultData;
                }
                goods_1.setPrice(goodsName.get(0).getPrice());
                goods_1.setShelfLife(goodsName.get(0).getShelfLife());
                goods_1.setTypeId(goodsName.get(0).getTypeId());
                goodsMapper.addGoods(goods_1);
                goodsList = goodsMapper.getGoodsBy(map.get("goodsName"),s);
                stockMapper.addStock(goodsList.get(0).getId(),Integer.parseInt(num),500);
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
            redisTemplate.delete("stock");
            stockChangeMapper.insertStockChange(stockChange);
        }
        redisTemplate.delete("stock");
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
        PageInfo<StockChange> pageInfo = new PageInfo<>(stockChangeBy, 3);
        List<StockChange> list = pageInfo.getList();
        for (StockChange stockChange : stockChangeBy) {
            Date createTime = stockChange.getTime();
            String formatTime = AppDateUtils.getFormatTime( "yyyy-MM-dd HH:mm:ss",createTime);
            stockChange.setDataTime(formatTime);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("pages", pageInfo.getPages());
        map.put("total",pageInfo.getTotal());
        map.put("start", pageInfo.getPageNum());
        System.out.println("=============================>"+"getStockChangeBy执行");
        return map;
    }
    public Map<String, Integer> getCheckStatistics(){
        Map<String,Integer> map=new HashMap<String, Integer>();
        map.put("checkin",stockChangeMapper.getCheckStatistics(1));
        map.put("checkout",stockChangeMapper.getCheckStatistics(0));
        return map;
    }

    public ResultData checkDelete(int id,int type){
        ResultData resultData = new ResultData();
        StockChange stockChangeById = stockChangeMapper.getStockChangeById(id);
        List<Stock> stocks = stockMapper.searchStock(stockChangeById.getGoodsId(), null);
        if(stocks.isEmpty()){
            redisTemplate.delete("stock");
            stockChangeMapper.deleteStockChange(id,type);
            redisTemplate.delete("stock");
            resultData.setCode(Code.SUCCESS);
            resultData.setMsg("删除成功");
            return resultData;
        }
        Stock stock = stocks.get(0);
        //出库
        if(type == 0){
            stock.setGoodsAmount(stock.getGoodsAmount()+stockChangeById.getAmount());
            stockMapper.updateStock(stock);
        }
        //入库
        else{
            if(stock.getGoodsAmount()-stockChangeById.getAmount()<0){
                resultData.setCode(Code.FALISE);
                resultData.setMsg("库存不足，无法删除");
                return resultData;
            }
            stock.setGoodsAmount(stock.getGoodsAmount()-stockChangeById.getAmount());
            stockMapper.updateStock(stock);
        }
        redisTemplate.delete("stock");
        stockChangeMapper.deleteStockChange(id,type);
        redisTemplate.delete("stock");
        resultData.setCode(Code.SUCCESS);
        resultData.setMsg("删除成功");
        return  resultData;
    }

     public ResultData checkChangeUpdate(StockChange stockChange){
         redisTemplate.delete("stock");
         stockChangeMapper.updateStockChange(stockChange);
         redisTemplate.delete("stock");
         ResultData resultData = new ResultData();
         resultData.setCode(200);
         resultData.setMsg("修改成功");
         return resultData;
     }

}
