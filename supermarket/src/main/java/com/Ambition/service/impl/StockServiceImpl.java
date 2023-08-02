package com.Ambition.service.impl;

import com.Ambition.Utils.Code;
import com.Ambition.dto.ResultData;
import com.Ambition.mapper.StockMapper;
import com.Ambition.pojo.Stock;
import com.Ambition.service.StockService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xml.internal.utils.res.IntArrayWrapper;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockServiceImpl implements StockService {

    @Resource
    private StockMapper stockMapper;

    @Resource
    private RedisTemplate redisTemplate;

    //全部库存
    public ResultData getAllStock(){
        Boolean stock1 = redisTemplate.hasKey("stock");
        ResultData stock = null;
        if(stock1.equals(true)){
            stock = (ResultData)redisTemplate.opsForValue().get("stock");
        }
        ResultData<Object> resultData = new ResultData<>();
        List<Stock> allStock = stockMapper.getAllStock();
        PageInfo<Stock> pages = new PageInfo<>(allStock);
        resultData.setCode(200);
        HashMap<String, Object> map = new HashMap<>();
        map.put("count", pages.getTotal());
        map.put("pages", pages.getPages());
        map.put("pagenum", pages.getPageNum());
        map.put("stock", pages.getList());
        resultData.setData(map);
        //缓存为空或者修改过
        if(stock == null){
            redisTemplate.opsForValue().set("stock",resultData);
            System.out.println("=================>执行了getAllStock方法");
        }
        else{
            System.out.println("=================>从redis中获取库存");
        }
        return resultData;
    }
    //查询库存
    public ResultData searchStock(String goodsName){
        ResultData<Object> resultData = new ResultData<>();
        List<Stock> stocks = stockMapper.searchStock(null,goodsName);
        PageInfo<Stock> pages = new PageInfo<>(stocks);
        if (stocks != null && !stocks.isEmpty()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("count", pages.getTotal());
            map.put("pages", pages.getPages());
            map.put("pagenum", pages.getPageNum());
            map.put("stock", pages.getList());
            resultData.setCode(Code.SUCCESS);
            resultData.setMsg("查询成功");
            resultData.setData(map);
        }else {
            resultData.setCode(Code.FALISE);
            resultData.setMsg("没有该商品的库存数据");
        }
        System.out.println("=================>执行searchStock方法");
        return resultData;
    }

    public Map<String, Object> underStock() {
        //ResultData resultData = new ResultData();
        List<Stock> stocks = stockMapper.underStock();
        PageInfo<Stock> pages = new PageInfo<>(stocks);
        System.out.println(stocks);
        HashMap<String,Object> map=new HashMap<>();
        map.put("total",pages.getTotal());
        map.put("pages",pages.getPages());
        map.put("pagenum", pages.getPageNum());
        map.put("list",pages.getList());
       // resultData.setData(map);
        return map;
    }

    public ResultData getStockStatistics(){
        ResultData resultData = new ResultData();
        List<Map<String, String>> stockStatistics = stockMapper.getStockStatistics();
        resultData.setCode(Code.SUCCESS);
        resultData.setMsg("查询成功！");
        resultData.setData(stockStatistics);
        System.out.println(stockStatistics);
        return resultData;
    }
}
