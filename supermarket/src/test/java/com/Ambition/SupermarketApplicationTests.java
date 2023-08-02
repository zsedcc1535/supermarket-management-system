package com.Ambition;

import com.Ambition.dto.ResultData;
import com.Ambition.service.OrderService;
import com.Ambition.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;


@SpringBootTest
class SupermarketApplicationTests {

@Resource
private OrderService orderService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StockService stockService;

    @Test
    void contextLoads() {
    }

}
