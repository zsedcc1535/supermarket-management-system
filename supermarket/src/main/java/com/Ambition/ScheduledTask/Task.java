package com.Ambition.ScheduledTask;

import com.Ambition.mapper.GoodsMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Task {
    @Resource
    private GoodsMapper goodsMapper;
    //每天中午12点触发
    //@Scheduled(cron = "0 0 12 * * ?")
    //每20秒触发一次
    @Scheduled(cron = "0/20 * * * * ?")
    private void task() {
        goodsMapper.overdue();
        goodsMapper.discount();
    }
}