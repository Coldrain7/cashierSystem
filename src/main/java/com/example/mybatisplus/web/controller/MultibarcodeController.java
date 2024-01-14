package com.example.mybatisplus.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.MultibarcodeService;
import com.example.mybatisplus.model.domain.Multibarcode;


/**
 *
 *  前端控制器
 *
 *
 * @author harry
 * @since 2024-01-14
 * @version v1.0
 */
@Controller
@RequestMapping("/api/multibarcode")
public class MultibarcodeController {

    private final Logger logger = LoggerFactory.getLogger( MultibarcodeController.class );

    @Autowired
    private MultibarcodeService multibarcodeService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Multibarcode  multibarcode =  multibarcodeService.getById(id);
        return JsonResponse.success(multibarcode);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        multibarcodeService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateMultibarcode(Multibarcode  multibarcode) throws Exception {
        multibarcodeService.updateById(multibarcode);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Multibarcode
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Multibarcode  multibarcode) throws Exception {
        multibarcodeService.save(multibarcode);
        return JsonResponse.success(null);
    }
}

