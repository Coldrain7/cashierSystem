package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Book;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.model.dto.SortDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.CommodityService;
import com.example.mybatisplus.model.domain.Commodity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 前端控制器
 *
 * @author harry
 * @version v1.0
 * @since 2024-01-06
 */
@Controller
@RequestMapping("/api/commodity")
public class CommodityController {

    private final Logger logger = LoggerFactory.getLogger(CommodityController.class);

    @Autowired
    private CommodityService commodityService;

    /**
     * 描述：根据Id 查询
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCommodity/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        Commodity commodity = commodityService.getById(id);
        return JsonResponse.success(commodity);
    }

    /**
     * 描述：根据Id删除
     *
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
     *
     * @param commodity 新商品实体
     * @return boolean
     */
    @ResponseBody
    @GetMapping("/createCommodity")
    public JsonResponse createCommodity(Commodity commodity) {
        // QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        // wrapper.eq("barcode", commodity.getBarcode()).eq("sup_id", commodity.getSupId());
        // Commodity c = commodityService.getOne(wrapper);
        Commodity c = commodityService.getUniqueBarcode(commodity);
        if (c == null) {
            if(commodity.getPrice() == null){
                return JsonResponse.success(null,"销售价不能为空");
            }
            if(commodity.getPurchasePrice() == null){
                return JsonResponse.success(null,"进货价不能为空");
            }
            if (commodity.getWholesalePrice() == null) {
                commodity.setWholesalePrice(commodity.getPrice());
            }
            if(commodity.getName()==null){
                return JsonResponse.success(null,"商品名称不能为空");
            }
            long id = commodityService.insert(commodity);
            return JsonResponse.success(id);
        }
        return JsonResponse.success(null, "商品条码已存在");
    }

    /**
     * 基本的查询商品功能
     *
     * @param pageDTO
     * @param commodity
     * @return
     */
    @ResponseBody
    @GetMapping("/commodityPage")
    public JsonResponse commodityPage(PageDTO pageDTO, Commodity commodity) {
        Page<Commodity> page = commodityService.commodityPage(pageDTO, commodity);
        return JsonResponse.success(page);
    }

    /**
     * 包含排序的查询商品资料功能
     *
     * @param pageDTO
     * @param commodity
     * @param sortDTO
     * @return
     */
    @ResponseBody
    @GetMapping("/commodityPageInOrder")
    public JsonResponse commodityPage(PageDTO pageDTO, Commodity commodity, SortDTO sortDTO) {
        Page<Commodity> page = commodityService.commodityPageInOrder(pageDTO, commodity, sortDTO);
        return JsonResponse.success(page);
    }

    /**
     * 根据id更新商品
     *
     * @param commodity 商品实体，包含要修改的属性值
     * @return boolean
     */
    @ResponseBody
    @GetMapping("/updateCommodity")
    public JsonResponse updateCommodity(Commodity commodity) {
        boolean result = commodityService.updateById(commodity);
        return JsonResponse.success(result);
    }

    /**
     * 基础的搜索商品功能，可按条码、名称和分类搜索，并能排序
     *
     * @param pageDTO   分页器
     * @param commodity 包含搜索的条码、名称和分类信息，条码名称不能为空
     * @param sortDTO   包含排序的列名及升序或降序关键字
     * @return Page<Commodity>
     */
    @ResponseBody
    @GetMapping("/searchCommodities")
    public JsonResponse searchCommodities(PageDTO pageDTO, Commodity commodity, SortDTO sortDTO) {
        //未对commodity的name与barcode赋值，不满足条件不能进行关键字搜索，需要前端对这两个值赋值
        Page<Commodity> page = commodityService.searchCommodities(pageDTO, commodity, sortDTO);
        return JsonResponse.success(page);
    }


    /**
     * 高级搜索
     * @param map 包含分类，供应商，会员折扣，创建时间from,to，价格区间，库存区间，关键字，规格，一品多码，排序，页码信息
     * @return Page
     */
    @ResponseBody
    @PostMapping("/advanceSearch")
    public JsonResponse advanceSearch(@RequestBody Map<String, Object> map) {
        // 分类，供应商，会员折扣，创建时间from,to，价格区间，库存区间，关键字，规格，一品多码
        ArrayList<Integer> claIds = (ArrayList<Integer>) map.get("claIds");
        ArrayList<Integer> supplierIds = (ArrayList<Integer>) map.get("supplierIds");
        Commodity commodity = new Commodity();
        if(map.get("supId") == null){
            return JsonResponse.success(null,"supId为空");
        }
        commodity.setSupId(Integer.parseInt(map.get("supId").toString()));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(!(map.get("timeFrom") == null || map.get("timeFrom").equals(""))){
            commodity.setCreateTime(LocalDateTime.parse(map.get("timeFrom").toString(), df));//起始时间
        }
        if(!(map.get("priceFrom") == null || map.get("priceFrom").equals(""))){
            commodity.setPrice(Double.parseDouble(map.get("priceFrom").toString()));//起始价格
        }
        if(!(map.get("inventoryFrom") == null || map.get("inventoryFrom").equals(""))){
            commodity.setInventory(Double.parseDouble(map.get("inventoryFrom").toString()));//起始库存
        }
        if(!(map.get("isDiscount") == null || map.get("isDiscount").equals(""))){
            commodity.setIsDiscount(Boolean.parseBoolean(map.get("isDiscount").toString()));
        }
        commodity.setName(map.get("name").toString());
        commodity.setBarcode(commodity.getName());
        commodity.setSpecification(map.get("specification").toString());
        if(!(map.get("isMultibarcode") == null || map.get("isMultibarcode").equals(""))){
            commodity.setIsMultibarcode(Boolean.parseBoolean(map.get("isMultibarcode").toString()));
        }
        Commodity secondCommodity = new Commodity();
        if(!(map.get("timeTo") == null || map.get("timeTo").equals(""))){
            secondCommodity.setCreateTime(LocalDateTime.parse(map.get("timeTo").toString(), df));//结束时间
        }
        if(!(map.get("priceTo") == null || map.get("priceTo").equals(""))){
            secondCommodity.setPrice(Double.parseDouble(map.get("priceTo").toString()));//结束价格
        }
        if(!(map.get("inventoryTo") == null || map.get("inventoryTo").equals(""))){
            secondCommodity.setInventory(Double.parseDouble(map.get("inventoryTo").toString()));//结束库存
        }
        SortDTO sortDTO = new SortDTO();
        if(map.get("prop") != null){
            sortDTO.setProp(map.get("prop").toString());
        }
        if(map.get("order")!= null){
            sortDTO.setOrder(map.get("order").toString());
        }
        if(map.get("pageNo") == null||map.get("pageSize") == null||
                map.get("pageNo").equals("")||map.get("pageSize").equals("")){
            return JsonResponse.success(null, "page信息为空");
        }
        Page<Commodity> page = new Page<>(Integer.parseInt(map.get("pageNo").toString()),
                Integer.parseInt(map.get("pageSize").toString()));
        page = commodityService.advanceSearch(page, claIds, supplierIds, commodity, secondCommodity, sortDTO);
        return JsonResponse.success(page);
    }

    /**
     * 基本的导出商品方法，可接收一个commodity类作为查询条件
     * @param httpServletResponse 响应
     * @param commodity 包含查询条件的商品类
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/exportCommodities")
    public void exportCommodities(HttpServletResponse httpServletResponse, @RequestBody Commodity commodity) throws IOException {
        commodityService.exportCommodities(httpServletResponse, commodity);
    }
    @ResponseBody
    @PostMapping("/advanceExport")
    public void advanceExport(HttpServletResponse httpServletResponse, @RequestBody Map<String, Object> map) throws IOException {
        ArrayList<Integer> claIds = (ArrayList<Integer>) map.get("claIds");
        ArrayList<Integer> supplierIds = (ArrayList<Integer>) map.get("supplierIds");
        Commodity commodity = new Commodity();
        if(map.get("supId") == null){
           return;
        }
        commodity.setSupId(Integer.parseInt(map.get("supId").toString()));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(!(map.get("timeFrom") == null || map.get("timeFrom").equals(""))){
            commodity.setCreateTime(LocalDateTime.parse(map.get("timeFrom").toString(), df));//起始时间
        }
        if(!(map.get("priceFrom") == null || map.get("priceFrom").equals(""))){
            commodity.setPrice(Double.parseDouble(map.get("priceFrom").toString()));//起始价格
        }
        if(!(map.get("inventoryFrom") == null || map.get("inventoryFrom").equals(""))){
            commodity.setInventory(Double.parseDouble(map.get("inventoryFrom").toString()));//起始库存
        }
        if(!(map.get("isDiscount") == null || map.get("isDiscount").equals(""))){
            commodity.setIsDiscount(Boolean.parseBoolean(map.get("isDiscount").toString()));
        }
        commodity.setName(map.get("name").toString());
        commodity.setBarcode(commodity.getName());
        commodity.setSpecification(map.get("specification").toString());
        if(!(map.get("isMultibarcode") == null || map.get("isMultibarcode").equals(""))){
            commodity.setIsMultibarcode(Boolean.parseBoolean(map.get("isMultibarcode").toString()));
        }
        Commodity secondCommodity = new Commodity();
        if(!(map.get("timeTo") == null || map.get("timeTo").equals(""))){
            secondCommodity.setCreateTime(LocalDateTime.parse(map.get("timeTo").toString(), df));//结束时间
        }
        if(!(map.get("priceTo") == null || map.get("priceTo").equals(""))){
            secondCommodity.setPrice(Double.parseDouble(map.get("priceTo").toString()));//结束价格
        }
        if(!(map.get("inventoryTo") == null || map.get("inventoryTo").equals(""))){
            secondCommodity.setInventory(Double.parseDouble(map.get("inventoryTo").toString()));//结束库存
        }
        commodityService.exportCommodities(httpServletResponse, claIds, supplierIds, commodity, secondCommodity);
    }
}

