package com.Ambition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//@ComponentScan(basePackages = {"com.Ambition.mapper"})
@SpringBootApplication
@EnableAsync    //开启异步功能
@EnableScheduling //开启定时任务
public class SupermarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupermarketApplication.class, args);
    }

}
