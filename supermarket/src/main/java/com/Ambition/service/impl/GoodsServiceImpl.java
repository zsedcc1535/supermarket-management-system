package com.Ambition.service.impl;

import com.Ambition.Utils.AppDateUtils;
import com.Ambition.Utils.Code;
import com.Ambition.dto.ResultData;
import com.Ambition.mapper.GoodsMapper;
import com.Ambition.mapper.GoodsTypeMapper;
import com.Ambition.mapper.StockMapper;
import com.Ambition.pojo.Expiration;
import com.Ambition.pojo.Goods;
import com.Ambition.pojo.GoodsType;
import com.Ambition.pojo.Order;
import com.Ambition.service.GoodsService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsTypeMapper goodsTypeMapper;

    @Resource
    private StockMapper stockMapper;

    public ResultData GetAllGoods(){
        ResultData<Object> resultData = new ResultData<>();
        List<Goods> allGoods = goodsMapper.getAllGoods();
        PageInfo<Goods> pages = new PageInfo<>(allGoods);
        resultData.setCode(200);
        HashMap<String, Object> map = new HashMap<>();
        for (Goods goods : allGoods) {
            Date createTime = goods.getProductTime();
            String formatTime = AppDateUtils.getFormatTime( "yyyy-MM-dd HH:mm:ss",createTime);
            goods.setMyDate(formatTime);
        }
        map.put("total", pages.getTotal());
        map.put("pages", pages.getPages());
        map.put("pagenum", pages.getPageNum());
        map.put("list", pages.getList());
        resultData.setData(map);
        System.out.println("=================>执行GetAllGoods方法");
        return resultData;
    }
    public ResultData addGoods(String goodsName, String name, String goodsDesc, int shelfLife, String productTime,String price) {
        ResultData resultData = new ResultData();
        Date formatTime = null;
        try {
            formatTime = AppDateUtils.toDate(productTime);
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
        List<Goods> goodsBy = goodsMapper.getGoodsBy(goodsName,null);
        if(!goodsBy.isEmpty()){
            resultData.setCode(Code.FALISE);
            resultData.setMsg("商品已经存在");
            return resultData;
        }
        else{
            resultData.setCode(Code.SUCCESS);
            Goods goods = new Goods();
            goods.setGoodsName(goodsName);
            GoodsType goodsType = goodsTypeMapper.selectGoodsTypeBy(null, name);
            goods.setTypeId(goodsType.getId());
            goods.setGoodsDesc(goodsDesc);
            goods.setShelfLife(shelfLife);
            goods.setProductTime(formatTime);
            goods.setPrice(price);
            goodsMapper.addGoods(goods);
            stockMapper.addStock(goodsType.getId(),null,null);
        }
        resultData.setCode(Code.SUCCESS);
        System.out.println("=================>执行addGoods方法");
        return resultData;
    }

    public ResultData deleteGoods(int id){
        ResultData resultData = new ResultData();
        goodsMapper.deleteGoods(id);
        stockMapper.deleteStock(id);
        resultData.setCode(Code.SUCCESS);
        resultData.setMsg("商品删除成功");
        System.out.println("=================>执行deleteGoods方法");
        return resultData;
    }

    public ResultData updateGoods(int id, String goodsName, String name, String goodsDesc, int shelfLife){
        Goods goods = new Goods();
        GoodsType goodsType = goodsTypeMapper.selectGoodsTypeBy(null, name);
        goods.setId(id);
        goods.setGoodsName(goodsName);
        goods.setTypeId(goodsType.getId());
        goods.setGoodsDesc(goodsDesc);
        goods.setShelfLife(shelfLife);
        goodsMapper.updateGoods(goods);
        ResultData resultData = new ResultData();
        resultData.setCode(Code.SUCCESS);
        resultData.setMsg("商品修改成功");
        System.out.println("=================>执行updateGoods方法");
        return resultData;
    }

    public Map<String, Object> change(){
        List<Expiration> list = goodsMapper.expiration();
        PageInfo<Expiration> pages = new PageInfo<>(list);
        HashMap<String,Object> map=new HashMap<>();
        map.put("total",pages.getTotal());
        map.put("pages",pages.getPages());
        map.put("pagenum", pages.getPageNum());
        map.put("list",pages.getList());
        System.out.println(list);
        return map;
    }
}
