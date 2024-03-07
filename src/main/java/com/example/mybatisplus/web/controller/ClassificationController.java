package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
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
        boolean res = classificationService.removeById(id);
        return JsonResponse.success(res);
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
     * 新增父分类
     * @param classification 需要包含supId与classification
     * @return 保存成功status返回true，否则返回false
     */
    @RequestMapping(value = "/createClassification", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(@RequestBody Classification  classification){
        if(StringUtils.isBlank(classification.getClassification())){
            return JsonResponse.failure("新增分类失败：分类名不能为空");
        }
        QueryWrapper<Classification> wrapper = new QueryWrapper<>();
        wrapper.eq("classification", classification.getClassification())
                .eq("sup_id", classification.getSupId());
        if(classificationService.getOne(wrapper)!= null){
            return JsonResponse.failure("新增分类失败：分类名已存在");
        }
        try{
            int id = classificationService.insert(classification);
            return JsonResponse.success(id, "新增分类成功");
        }catch (DataIntegrityViolationException e){
            return JsonResponse.failure("新增分类失败");
        }
    }

    /**
     * 查询店铺的所有父子分类，包含分类的所有属性
     * @param supId 店铺id
     * @return List<Classification>
     */
    @ResponseBody
    @GetMapping("/getOptions")
    public JsonResponse getOptions(Integer supId){
        List<Classification> classifications = classificationService.getSupClassifications(supId);
        return JsonResponse.success(classifications);
    }

    /**
     * 查询分类名称相似的分类
     * @param classification 必须包含supId
     * @return 分类名称相似的分类，如果有子分类或父分类则一同返回
     */
    @ResponseBody
    @GetMapping("/searchClassifications")
    public JsonResponse searchClassifications(Classification classification){
        if(StringUtils.isNotBlank(classification.getClassification())){
            return JsonResponse.success(classificationService.searchClassifications(classification));
        }else{
            return JsonResponse.success(classificationService.getSupClassifications(classification.getSupId()));
        }

    }
}

