package com.example.mybatisplus.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.SupplierService;
import com.example.mybatisplus.model.domain.Supplier;

import java.util.ArrayList;
import java.util.List;


/**
 *
 *  前端控制器
 *
 *
 * @author harry
 * @since 2024-01-27
 * @version v1.0
 */
@Controller
@RequestMapping("/api/supplier")
public class SupplierController {

    private final Logger logger = LoggerFactory.getLogger( SupplierController.class );

    @Autowired
    private SupplierService supplierService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Supplier  supplier =  supplierService.getById(id);
        return JsonResponse.success(supplier);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        supplierService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateSupplier(Supplier  supplier) throws Exception {
        supplierService.updateById(supplier);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Supplier
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Supplier  supplier) throws Exception {
        supplierService.save(supplier);
        return JsonResponse.success(null);
    }
    @ResponseBody
    @GetMapping("/getOptions")
    public JsonResponse getOptions(Integer supId){
        List<Supplier> suppliers = supplierService.getSupSuppliers(supId);
        return JsonResponse.success(suppliers);
    }
}

