package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.utls.ExcelUtils;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.model.dto.SortDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.CommodityService;
import com.example.mybatisplus.model.domain.Commodity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


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
     * 根据条码和店铺id查询商品
     * @param commodity 需要包含barcode和supId
     * @return 查询失败返回错误信息，成功则返回数据，包含所有属性
     */
    @ResponseBody
    @GetMapping("/getCommodity")
    public JsonResponse getCommodity(Commodity commodity){
        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        wrapper.like("barcode", commodity.getBarcode()).eq("sup_id", commodity.getSupId());
        try{
            Commodity c = commodityService.getOneCommodity(wrapper);
            if(c == null){
                return JsonResponse.failure("商品不存在");
            }else {
                return JsonResponse.success(c);
            }
        }catch (RuntimeException e){
            return JsonResponse.failure(e.getMessage());
        }
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
     * 根据id更新商品,对规格以及组合拆分的商品进货价进行了检验
     * @param commodity 商品实体，包含要修改的属性值和id
     * @return 修改成功返回true,否则返回false
     */
    @ResponseBody
    @GetMapping("/updateCommodity")
    public JsonResponse updateCommodity(Commodity commodity) {
        Pattern pattern = Pattern.compile("^\\d+x\\d+$");
        if(StringUtils.isNotBlank(commodity.getSpecification())){
            if(!pattern.matcher(commodity.getSpecification()).matches()){
                return JsonResponse.failure("规格形式不匹配");
            }
        }
        Commodity c = commodityService.getById(commodity.getId());
        if(!c.getPurchasePrice().equals(commodity.getPurchasePrice())){//进货价发生变化
            if(c.getParent() == null){//商品可能是组合后商品或普通商品
                QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
                wrapper.eq("sup_id", commodity.getSupId()).eq("parent", commodity.getId());
                Commodity separateCommodity = commodityService.getOne(wrapper);
                if(separateCommodity != null){// 商品可拆分，还要更新拆分后的进价
                    Double num = Double.parseDouble(separateCommodity.getSpecification().substring(2));
                    separateCommodity.setPurchasePrice(commodity.getPurchasePrice()/num);
                    boolean res = commodityService.updateById(separateCommodity);
                    return JsonResponse.success(res && commodityService.updateById(commodity),"修改失败，请检查商品拆分后的进货价");
                }else{//商品为普通商品，只用更新commodity
                    return JsonResponse.success(commodityService.updateById(commodity), "保存失败");
                }
            }else{//商品为拆分后的商品，还需要更新组合后的商品进货价
                QueryWrapper wrapper = new QueryWrapper<>();
                wrapper.eq("id", c.getParent());
                Commodity combinedCommodity = commodityService.getOne(wrapper);
                Double num = Double.parseDouble(c.getSpecification().substring(2));
                combinedCommodity.setPurchasePrice(num * commodity.getPurchasePrice());
                boolean res = commodityService.updateById(combinedCommodity);
                return JsonResponse.success(res && commodityService.updateById(commodity), "修改失败，请检查商品组合后的进货价");
            }
        }else{//进货价无变化
            boolean result = commodityService.updateById(commodity);
            return JsonResponse.success(result,"保存失败");
        }
    }

    /**
     * 删除组合拆分关系，实际是将子商品的parent置为null
     * @param commodity 必须包含id
     * @return data为true如果成功修改，否则为false
     */
    @ResponseBody
    @PostMapping("/deleteCombination")
    public JsonResponse deleteCombination(@RequestBody Commodity commodity){
        UpdateWrapper<Commodity> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",commodity.getId()).set("parent", null);
        boolean res = commodity.update(wrapper);
        return JsonResponse.success(res);
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

    /**
     * 对应高级搜索的导出方法
     * @param httpServletResponse http响应
     * @param map 查询条件
     * @throws IOException
     */
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

    /**
     * 下载导入模板
     * @param httpServletResponse http响应
     */
    @ResponseBody
    @PostMapping("/exportTemplate")
    public void exportTemplate(HttpServletResponse httpServletResponse){
        commodityService.exportTemplate(httpServletResponse);
    }

    /**
     * 前端要传入supId以在后端插入时验证店铺是否已有该条码
     * @param multipartFile 文件
     * @param supId 需要包含supId
     * @return 成功或失败信息
     */
    @ResponseBody
    @PostMapping("/importCommodities")
    public JsonResponse importCommodities(@RequestParam("file")MultipartFile multipartFile, @RequestParam Integer supId){
        try {
            // int supId = Integer.parseInt(map.get("supId").toString());
            List<String[]> strings = ExcelUtils.readExcel(multipartFile);
            return commodityService.importCommodities(strings, supId);
        } catch (IOException e) {
            return JsonResponse.failure("读取导入文件错误");
            // throw new RuntimeException(e);
        }
    }

    /**
     * 获取所有的组合拆分商品
     * @param supId 店铺id，不能为空
     * @return 返回所有的组合拆分商品，没有店铺id则没有数据
     */
    @ResponseBody
    @GetMapping("/getCombinations")
    public JsonResponse getCombinations(Integer supId){
        return JsonResponse.success(commodityService.getCombinations(supId));
    }

    /**
     * 根据关键字查询组合拆分商品
     * @param commodity 必须包含supId，name作为关键字
     * @return 返回查询到的Combination数据
     */
    @ResponseBody
    @GetMapping("/searchCombinations")
    public JsonResponse searchCombinations(Commodity commodity){
        return JsonResponse.success(commodityService.searchCombinations(commodity));
    }
}

