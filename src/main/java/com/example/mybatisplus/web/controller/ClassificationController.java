package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.ClassificationService;
import com.example.mybatisplus.model.domain.Classification;

import java.util.List;


/**
 *
 *  前端控制器
 *
 *
 * @author harry
 * @since 2024-01-19
 * @version v1.0
 */
@Controller
@RequestMapping("/api/classification")
public class ClassificationController {

    private final Logger logger = LoggerFactory.getLogger( ClassificationController.class );

    @Autowired
    private ClassificationService classificationService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Classification  classification =  classificationService.getById(id);
        return JsonResponse.success(classification);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        classificationService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateClassification(Classification  classification) throws Exception {
        classificationService.updateById(classification);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Classification
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Classification  classification) throws Exception {
        classificationService.save(classification);
        return JsonResponse.success(null);
    }

    /**
     * 查询店铺的所有分类，包含分类的所有属性
     * @param supId 店铺id
     * @return List<Classification>
     */
    @ResponseBody
    @GetMapping("/getOptions")
    public JsonResponse getOptions(Integer supId){
        List<Classification> classifications = classificationService.getSupClassifications(supId);
        return JsonResponse.success(classifications);
    }
}

