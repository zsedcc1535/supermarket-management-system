package com.Ambition.mapper;

import com.Ambition.pojo.LoginRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginRecordMapper {
    //添加登录记录
    void addLoginRecord(LoginRecord loginRecord);
}
