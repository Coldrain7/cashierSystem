package com.example.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.mapper.AdminMapper;
import com.example.mybatisplus.mapper.BookMapper;
import com.example.mybatisplus.mapper.MultibarcodeMapper;
import com.example.mybatisplus.model.domain.*;
import com.example.mybatisplus.service.AdminService;
import com.example.mybatisplus.service.BookService;
import com.example.mybatisplus.service.SupermarketService;
import com.example.mybatisplus.service.WorkerService;
import com.example.mybatisplus.service.impl.AdminServiceImpl;
import com.example.mybatisplus.web.controller.MultibarcodeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MybatisplusApplicationTests {
    @Autowired
    private SupermarketService supermarketService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private MultibarcodeController multibarcodeController;
    @Test
    public void createSupermarketTest(){
        Supermarket supermarket = new Supermarket().setName("测试超市");
        int num = supermarketService.register(supermarket);
        System.out.println(num);
    }
    @Test
    public void updateWorker(){
        Worker worker = new Worker().setId(100000000).setPassword("1234567").setMail("3024007008@qq.com");
        System.out.println(workerService.updateById(worker));
        System.out.println(""==null);
    }





}
