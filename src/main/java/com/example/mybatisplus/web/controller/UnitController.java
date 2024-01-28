package com.example.mybatisplus.web.controller;

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
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        unitService.removeById(id);
        return JsonResponse.success(null);
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
    * 描述:创建Unit
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Unit  unit) throws Exception {
        unitService.save(unit);
        return JsonResponse.success(null);
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

