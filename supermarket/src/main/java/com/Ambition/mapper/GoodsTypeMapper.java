package com.Ambition.mapper;

import com.Ambition.pojo.GoodsType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsTypeMapper {
    GoodsType selectGoodsTypeBy(Integer id,String name);
}