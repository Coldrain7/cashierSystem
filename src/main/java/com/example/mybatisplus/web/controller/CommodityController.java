package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Book;
import com.example.mybatisplus.model.dto.PageDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.CommodityService;
import com.example.mybatisplus.model.domain.Commodity;


/**
 *
 *  前端控制器
 *
 *
 * @author harry
 * @since 2024-01-06
 * @version v1.0
 */
@Controller
@RequestMapping("/api/commodity")
public class CommodityController {

    private final Logger logger = LoggerFactory.getLogger( CommodityController.class );

    @Autowired
    private CommodityService commodityService;

    /**
     * 描述：根据Id 查询
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCommodity/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Commodity  commodity =  commodityService.getById(id);
        return JsonResponse.success(commodity);
    }

    /**
     * 描述：根据Id删除
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteCommodity/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        boolean result = commodityService.removeById(id);
        return JsonResponse.success(result);
    }

    /**
     * 创建新商品
     * @param commodity 新商品实体
     * @return boolean
     */
    @ResponseBody
    @GetMapping("/createCommodity")
    public JsonResponse createCommodity(Commodity  commodity){
        // QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        // wrapper.eq("barcode", commodity.getBarcode()).eq("sup_id", commodity.getSupId());
        // Commodity c = commodityService.getOne(wrapper);
        Commodity c = commodityService.getUniqueBarcode(commodity);
        if(c == null){
            long id = commodityService.insert(commodity);
            return JsonResponse.success(id);
        }
        return JsonResponse.success(null, "商品条码已存在");
    }

    /**
     * 基本的查询商品功能，还差一品多码表没有联合
     * @param pageDTO
     * @param commodity
     * @return
     */
    @ResponseBody
    @GetMapping("/commodityPage")
    public JsonResponse commodityPage(PageDTO pageDTO, Commodity commodity)
    {
        Page<Commodity> page = commodityService.commodityPage(pageDTO, commodity);
        return JsonResponse.success(page);
    }
    /**
     * 根据id更新商品
     * @param commodity 商品实体，包含要修改的属性值
     * @return boolean
     */
    @ResponseBody
    @GetMapping("/updateCommodity")
    public JsonResponse updateCommodity(Commodity commodity){
        boolean result = commodityService.updateById(commodity);
        return JsonResponse.success(result);
    }
}

