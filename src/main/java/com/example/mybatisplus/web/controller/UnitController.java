package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.UnitService;
import com.example.mybatisplus.model.domain.Unit;

import java.util.List;


/**
 *
 *  前端控制器
 *
 *
 * @author harry
 * @since 2024-01-25
 * @version v1.0
 */
@Controller
@RequestMapping("/api/unit")
public class UnitController {

    private final Logger logger = LoggerFactory.getLogger( UnitController.class );

    @Autowired
    private UnitService unitService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Unit  unit =  unitService.getById(id);
        return JsonResponse.success(unit);
    }

    /**
     * 根据id删除单位
     * @param id
     * @return boolean
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Integer id) throws Exception {
        boolean result = unitService.removeById(id);
        return JsonResponse.success(result);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateUnit(Unit  unit) throws Exception {
        unitService.updateById(unit);
        return JsonResponse.success(null);
    }


    /**
     * 创建新单位，数据库已存在此单位就不新建
     * @param unit 需要supId和unit属性不为空
     * @return boolean
     */
    @RequestMapping(value = "/createUnit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(@RequestBody Unit  unit) {
        QueryWrapper<Unit> wrapper = new QueryWrapper<>();
        if(StringUtils.isBlank(unit.getUnit())){
            return JsonResponse.success(false, "创建失败：单位名称为空");
        }
        if(unit.getSupId() == null){
            return JsonResponse.success(false, "创建失败");
        }
        wrapper.eq("sup_id", unit.getSupId()).eq("unit", unit.getUnit());
        Unit u = unitService.getOne(wrapper);
        boolean result = false;
        if(u == null){
            result = unitService.save(unit);
        }
        return JsonResponse.success(result);
    }

    /**
     * 获取超市所有的单位
     * @param supId 超市id
     * @return List
     */
    @ResponseBody
    @GetMapping("/getOptions")
    public JsonResponse getOptions(Integer supId){
        List<Unit> units = unitService.getSupUnits(supId);
        return JsonResponse.success(units);
    }
}

