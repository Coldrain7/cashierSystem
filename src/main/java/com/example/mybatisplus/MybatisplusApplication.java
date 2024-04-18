package com.example.mybatisplus;

import com.example.mybatisplus.service.CommodityService;
import com.example.mybatisplus.service.EventService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
@EnableScheduling
@SpringBootApplication
@MapperScan({"com.example.mybatisplus.mapper"})
public class MybatisplusApplication {


    @Autowired
    private EventService eventService;
    @Autowired
    private CommodityService commodityService;
    @Scheduled(fixedRate = 300000) // 每5分钟执行一次，单位毫秒
    public void scheduleEventPriceUpdate() {
        eventService.updateCommodityPricesBasedOnEvents();
        commodityService.updateCombinationInventory();
    }

    public static void main(String[] args) {
        SpringApplication.run(MybatisplusApplication.class, args);
    }
}
