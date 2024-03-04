package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.model.domain.Commodity;
import com.example.mybatisplus.service.CommodityService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.MultibarcodeService;
import com.example.mybatisplus.model.domain.Multibarcode;

import java.util.List;


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
    @Autowired
    private CommodityService commodityService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Integer id)throws Exception {
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
     * 批量更新一品多码表的条码
     * @param multibarcodes List<Multibarcode>
     * @return boolean
     */
    @ResponseBody
    @PostMapping("/updateBarcodes")
    public JsonResponse updateMultibarcode(@RequestBody List<Multibarcode> multibarcodes){
        boolean result = multibarcodeService.updateBatchById(multibarcodes);
        return JsonResponse.success(result);
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

    /**
     * 获取条形码
     * @param id 商品id
     * @return List
     */
    @ResponseBody
    @GetMapping("/getBarcodes")
    public JsonResponse getBarcodes(Integer id){
        QueryWrapper<Multibarcode> wrapper = new QueryWrapper<>();
        wrapper.eq("com_id", id);
        List<Multibarcode> barcodes = multibarcodeService.list(wrapper);
        return JsonResponse.success(barcodes);
    }

    /**
     * 添加条形码
     * @param barcodes List<Multibarcode>
     * @return boolean
     */
    @ResponseBody
    @PostMapping ("/addBarcodes/{supId}")
    public JsonResponse addBarcodes(@RequestBody List<Multibarcode> barcodes, @PathVariable("supId")Integer supId){
        Commodity commodity = new Commodity();
        for(int i=0; i<barcodes.size(); i++){
            if(barcodes.get(i).getComId() == null){
                return JsonResponse.success(false, "缺少商品id");
            }
            commodity.setBarcode(barcodes.get(i).getBarcode());
            commodity.setSupId(supId);
            Commodity c = commodityService.getUniqueBarcode(commodity);
            if(c == null){
                multibarcodeService.save(barcodes.get((i)));
            }else{
                return JsonResponse.success(false, commodity.getBarcode().toString() + "已存在");
            }
        }
        return JsonResponse.success(true);
    }

    /**
     * 批量删除条形码
     * @param barcodes List<String>类型，要删除的条形码id
     * @return boolean
     */
    @ResponseBody
    @PostMapping("/deleteBarcodes")
    public JsonResponse deleteBarcodes(@RequestBody List<Integer> barcodes){
        boolean result = multibarcodeService.removeByIds(barcodes);
        return JsonResponse.success(result);
    }
}

