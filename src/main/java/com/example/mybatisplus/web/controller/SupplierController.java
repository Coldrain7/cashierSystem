package com.example.mybatisplus.web.controller;

import com.example.mybatisplus.model.dto.SortDTO;
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
     * 根据id查询供应商
     * @param id 供应商id
     * @return 返回完整供应商信息
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id){
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
     * 更新供应商信息
     * @param supplier 要修改的供应商信息
     * @return 返回修改成功或失败的信息
     */
    @ResponseBody
    @GetMapping("/updateSupplier")
    public JsonResponse updateSupplier(Supplier  supplier){
        if(supplier.getSupId() == null){
            return JsonResponse.failure("更新失败：没有店铺id");
        }else if(supplier.getName() == null){
            return JsonResponse.failure("更新失败：没有供应商名称");
        } else if (supplier.getNumber() == null) {
            return JsonResponse.failure("更新失败：没有编号");
        }else if(supplier.getId() == null){
            return JsonResponse.failure("更新失败：没有供应商id");
        }else{
            boolean res = supplierService.updateById(supplier);
            if(res){
                return JsonResponse.success(res, "更新成功");
            }else{
                return JsonResponse.failure("更新失败");
            }
        }
    }

    /**
     * 创建供应商
     * @param supplier 必须包含supId, name, number
     * @return 没有必须字段返回错误信息，否则返回成功信息与新供应商id
     */
    @RequestMapping(value = "/createSupplier", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(@RequestBody Supplier supplier){
        if(supplier.getSupId() == null){
            return JsonResponse.failure("创建失败：没有店铺id");
        }else if(supplier.getName() == null){
            return JsonResponse.failure("创建失败：没有供应商名称");
        } else if (supplier.getNumber() == null) {
            return JsonResponse.failure("创建失败：没有编号");
        }else{
            int id = supplierService.insert(supplier);
            return JsonResponse.success(id,"创建成功");
        }
    }

    /**
     * 获取供应商选项
     * @param supId 只传入店铺id
     * @return 返回完整的供应商信息
     */
    @ResponseBody
    @GetMapping("/getOptions")
    public JsonResponse getOptions(Integer supId){
        List<Supplier> suppliers = supplierService.getSupSuppliers(supId);
        return JsonResponse.success(suppliers);
    }

    /**
     * 供应商页面查询供应商信息，可以排序，可按关键字查询
     * @param supplier 关键字包含编号与名称，用Supplier的name字段存储，supId字段必须包含，sortDTO用于排序，可不排序
     * @return 返回完整的供应商信息
     */
    @ResponseBody
    @GetMapping("/getSuppliers")
    public JsonResponse getSuppliers(Supplier supplier, SortDTO sortDTO){
        return JsonResponse.success(supplierService.getSuppliers(supplier, sortDTO));
    }

}

