package com.example.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.mapper.AdminMapper;
import com.example.mybatisplus.mapper.BookMapper;
import com.example.mybatisplus.mapper.MultibarcodeMapper;
import com.example.mybatisplus.model.domain.*;
import com.example.mybatisplus.service.*;
import com.example.mybatisplus.service.impl.AdminServiceImpl;
import com.example.mybatisplus.web.controller.MultibarcodeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    @Autowired
    private IOcrService iOcrService;

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
    @Test
    public void idTest(){
        IdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();
        Record record = new Record();
        Number id = identifierGenerator.nextId(record);
        System.out.println(record.getId());
        System.out.println(id);
    }
    @Test
    public void ocrLocalPng() {
        try {
            InputStream inputStream=new FileInputStream("D://tessdata//pic.png");
            String result = iOcrService.recognizeText(inputStream);
            System.out.println(result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }




}
