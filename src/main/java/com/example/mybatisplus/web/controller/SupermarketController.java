package com.example.mybatisplus.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.SupermarketService;
import com.example.mybatisplus.model.domain.Supermarket;


/**
 *
 *  前端控制器
 *
 *
 * @author harry
 * @since 2024-01-07
 * @version v1.0
 */
@Controller
@RequestMapping("/api/supermarket")
public class SupermarketController {

    private final Logger logger = LoggerFactory.getLogger( SupermarketController.class );

    @Autowired
    private SupermarketService supermarketService;

    /**
     * 根据id获取店铺信息
     * @param id 店铺id
     * @return Supermarket
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Integer id){
        Supermarket  supermarket =  supermarketService.getById(id);
        return JsonResponse.success(supermarket);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        supermarketService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
     * 根据id更新店铺信息
     * @param supermarket 需要包含Supermarket所有属性信息
     * @return json data returns true if success, else false
     */
    @PostMapping("/updateSupermarket")
    @ResponseBody
    public JsonResponse updateSupermarket(@RequestBody Supermarket  supermarket){
        return JsonResponse.success(supermarketService.updateById(supermarket));
    }

    /**
     * 注册新店铺
     */
    @ResponseBody
    @PostMapping("/register")
    public JsonResponse createSupermarket(@RequestBody Supermarket supermarket){
        Integer supId = supermarketService.register(supermarket);
        return JsonResponse.success(supId.toString());
    }
}

