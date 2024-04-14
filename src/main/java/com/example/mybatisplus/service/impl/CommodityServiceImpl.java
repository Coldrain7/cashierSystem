package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.common.utls.ExcelUtils;
import com.example.mybatisplus.mapper.ClassificationMapper;
import com.example.mybatisplus.mapper.SupplierMapper;
import com.example.mybatisplus.mapper.UnitMapper;
import com.example.mybatisplus.model.domain.Book;
import com.example.mybatisplus.model.domain.Combination;
import com.example.mybatisplus.model.domain.Commodity;
import com.example.mybatisplus.mapper.CommodityMapper;
import com.example.mybatisplus.model.dto.*;
import com.example.mybatisplus.service.CommodityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.PatternDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * <p>
 * 商品相关属性 服务实现类
 * </p>
 *
 * @author harry
 * @since 2024-01-06
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;
    @Autowired
    private UnitMapper unitMapper;
    @Autowired
    private ClassificationMapper classificationMapper;
    @Autowired
    private SupplierMapper supplierMapper;
    @Override
    public Page<Commodity> commodityPage(PageDTO pageDTO, Commodity commodity) {
        Page<Commodity> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        commodityMapper.selectCommodityPage(page, commodity);
        return page;
    }

    @Override
    public Page<Commodity> commodityPageInOrder(PageDTO pageDTO, Commodity commodity, SortDTO sortDTO) {
        Page<Commodity> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        if(sortDTO.isContain()){
            commodityMapper.selectCommodityInOrder(page, commodity, sortDTO);
        }else{
            commodityMapper.selectCommodityPage(page, commodity);
        }
        return page;
    }

    @Override
    public long insert(Commodity commodity) {
        commodityMapper.insert(commodity);
        return commodity.getId();
    }

    @Override
    public Commodity getUniqueBarcode(Commodity commodity) {
        return commodityMapper.getUniqueBarcode(commodity);
    }

    /**
     * name和barcode都不为空才进行关键字搜索
     * @param pageDTO 分页数据
     * @param commodity 商品name和barcode和分类信息
     * @param sortDTO 分页信息
     * @return Page
     */
    @Override
    public Page<Commodity> searchCommodities(PageDTO pageDTO, Commodity commodity, SortDTO sortDTO) {
        Page<Commodity> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        boolean isSort = sortDTO.isContain();
        if(!commodity.getName().isEmpty() && !commodity.getBarcode().isEmpty()){//如果两个字段不为空
            commodityMapper.searchCommodityPage(page, commodity, sortDTO);
        }else if(isSort){//如果能排序
            commodityMapper.selectCommodityInOrder(page, commodity, sortDTO);
        }else {
            commodityMapper.selectCommodityPage(page,commodity);
        }
        return page;
    }

    @Override
    public Page<Commodity> advanceSearch(Page<Commodity> page, ArrayList<Integer> claIds, ArrayList<Integer> supplierIds,
                                         Commodity commodity, Commodity secondCommodity, SortDTO sortDTO) {
        sortDTO.isContain();
        return commodityMapper.advanceSearch(page, claIds, supplierIds, commodity, secondCommodity, sortDTO);
    }

    @Override
    public void exportCommodities(HttpServletResponse httpServletResponse, Commodity commodity) throws IOException {
//        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
//        if(StringUtils.isNotBlank(commodity.getName()) && StringUtils.isNotBlank(commodity.getBarcode())){
//            wrapper.or().like("name", commodity.getName()).or().like("barcode", commodity.getBarcode());
//        }
//        List<Commodity> commodities = commodityMapper.selectList(wrapper);
        SortDTO sortDTO = new SortDTO();
        List<Commodity> commodities = commodityMapper.searchCommodityPage(commodity, sortDTO);
        ExcelUtils.exportCommodities(httpServletResponse, commodities);
    }

    @Override
    public void exportCommodities(HttpServletResponse httpServletResponse, ArrayList<Integer> claIds, ArrayList<Integer> supplierIds, Commodity commodity, Commodity secondCommodity) throws IOException {
        SortDTO sortDTO = new SortDTO();
        List<Commodity> commodities = commodityMapper.advanceSearch(claIds, supplierIds, commodity, secondCommodity, sortDTO);
        ExcelUtils.exportCommodities(httpServletResponse, commodities);
    }

    @Override
    public void exportTemplate(HttpServletResponse httpServletResponse) {
        try {
            ExcelUtils.exportTemplate(httpServletResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonResponse importCommodities(List<String[]> stringList, int supId) {
        if (!stringList.get(0)[0].equals("商品名称")) {
            return JsonResponse.failure("商品名称表头错误");
        }
        if(!stringList.get(0)[1].equals("条码")){
            return JsonResponse.failure("条码表头错误");
        }
        if(!stringList.get(0)[2].equals("分类")){
            return JsonResponse.failure("分类表头错误");
        }
        if (!stringList.get(0)[3].equals("库存")) {
            return JsonResponse.failure("库存表头错误");
        }
        if(!stringList.get(0)[4].equals("单位")){
            return JsonResponse.failure("单位表头错误");
        }
        if(!stringList.get(0)[5].equals("规格")){
            return JsonResponse.failure("规格表头错误");
        }
        if (!stringList.get(0)[6].equals("进货价")) {
            return JsonResponse.failure("进货价表头错误");
        }
        if(!stringList.get(0)[7].equals("销售价")){
            return JsonResponse.failure("销售价表头错误");
        }
        if(!stringList.get(0)[8].equals("批发价")){
            return JsonResponse.failure("批发价表头错误");
        }
        if (!stringList.get(0)[9].equals("会员折扣")) {
            return JsonResponse.failure("会员折扣表头错误");
        }
        if(!stringList.get(0)[10].equals("供货商")){
            return JsonResponse.failure("供货商表头错误");
        }
        if(!stringList.get(0)[11].equals("生产日期")){
            return JsonResponse.failure("生产日期表头错误");
        }
        if(!stringList.get(0)[12].equals("保质期")){
            return JsonResponse.failure("保质期表头错误");
        }
        List<Commodity> commodities = new ArrayList<>();
        List<Integer> indexList = new ArrayList<>();
        Set<Integer> changedSet = new HashSet<>();
        List<UnitDTO> unitDTOList = unitMapper.getUnitList(supId);
        Map<String, Integer> unitMap = new HashMap<>();
        for (UnitDTO unitDTO : unitDTOList) {
            unitMap.put(unitDTO.getUnit(), unitDTO.getId());
        }
        List<ClassificationDTO> classificationDTOS = classificationMapper.getClassificationList(supId);
        Map<String, Integer> claMap = new HashMap<>();
        for (ClassificationDTO classificationDTO : classificationDTOS) {
            claMap.put(classificationDTO.getClassification(), classificationDTO.getId());
        }
        List<SupplierDTO> supplierDTOList = supplierMapper.getSupplierList(supId);
        Map<String, Integer> supplierMap = new HashMap<>();
        for (SupplierDTO supplierDTO : supplierDTOList) {
            supplierMap.put(supplierDTO.getName(), supplierDTO.getId());
        }
        Pattern pattern;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for(int i=1; i< stringList.size();i++){
            if(StringUtils.isBlank(stringList.get(i)[1])){//如果条形码为空
                indexList.add(i);
                continue;
            }
            Commodity c = new Commodity().setSupId(supId).setBarcode(stringList.get(i)[1]);
            //查询对应barcode的商品是否存在
            Commodity commodity = commodityMapper.getUniqueBarcode(c);
            if (commodity == null) {//不存在说明可以插入
                commodity = new Commodity();
                commodity.setSupId(supId);
                if (StringUtils.isNotBlank(stringList.get(i)[0])) {
                    commodity.setName(stringList.get(i)[0]);
                }else{
                    indexList.add(i);
                    continue;
                }
                if (NumberUtils.isParsable(stringList.get(i)[6])) {
                    commodity.setPurchasePrice(Double.parseDouble(stringList.get(i)[6]));
                }else {
                    indexList.add(i);
                    continue;
                }
                if (NumberUtils.isParsable(stringList.get(i)[7])) {
                    commodity.setPrice(Double.parseDouble(stringList.get(i)[7]));
                }else {
                    indexList.add(i);
                    continue;
                }
                commodity.setBarcode(stringList.get(i)[1]).setClaId(claMap.get(stringList.get(i)[2]));
                if(NumberUtils.isParsable(stringList.get(i)[3])){
                    commodity.setInventory(Double.parseDouble(stringList.get(i)[3]));
                }else{
                    changedSet.add(i);
                }
                commodity.setUnitId(unitMap.get(stringList.get(i)[4]));
                pattern = Pattern.compile("^\\d+x\\d+$");
                if(pattern.matcher(stringList.get(i)[5]).matches()){
                    commodity.setSpecification(stringList.get(i)[5]);
                }else {
                    changedSet.add(i);
                }

                if (StringUtils.isBlank(stringList.get(i)[8])) {//批发价没填写，默认设置为售价
                    commodity.setWholesalePrice(commodity.getPrice());
                } else if (NumberUtils.isParsable(stringList.get(i)[8])) {
                    commodity.setWholesalePrice(Double.parseDouble(stringList.get(i)[8]));
                }
                if(stringList.get(i)[9].equals("是") || stringList.get(i)[9].equals("否")){
                    commodity.setIsDiscount(stringList.get(i)[9].equals("是")?true:false);
                }else{
                    changedSet.add(i);
                }
                commodity.setClaId(claMap.get(stringList.get(i)[2]));
                commodity.setSupplierId(supplierMap.get(stringList.get(i)[10]));
                pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
                if (pattern.matcher(stringList.get(i)[11]).matches()) {
                    commodity.setCreateTime(LocalDate.parse(stringList.get(i)[11], df).atStartOfDay());
                }else{
                    changedSet.add(i);
                }
                if (NumberUtils.isParsable(stringList.get(i)[12])) {
                    commodity.setExpirationTime(Integer.parseInt(stringList.get(i)[12]));
                }else{
                    changedSet.add(i);
                }
                commodities.add(commodity);
            }else{//否则不插入
                indexList.add(i);
            }
        }
        this.saveBatch(commodities);
        if(indexList.isEmpty() && changedSet.isEmpty()){
            return JsonResponse.success("成功导入"+commodities.size()+"条数据","无错误数据");
        }
        StringBuilder stringBuilder = new StringBuilder();
        if(!indexList.isEmpty()){
            stringBuilder.append('第');
            stringBuilder.append(indexList.get(0));
            for(int i = 1; i<indexList.size();i++){
                stringBuilder.append(',');
                stringBuilder.append(indexList.get(i));
            }
            stringBuilder.append("条数据存在错误不能导入!");
        }
        if(!changedSet.isEmpty()){
            stringBuilder.append('第');
            Iterator<Integer> iterator = changedSet.iterator();
            if(iterator.hasNext()){
                stringBuilder.append(iterator.next());
            }
            while(iterator.hasNext()){
                stringBuilder.append(',');
                stringBuilder.append(iterator.next());
            }
            stringBuilder.append("条数据存在格式问题已修改格式插入");
        }
        if(commodities.isEmpty()){
            return JsonResponse.failure(stringBuilder.toString());
        }
        return JsonResponse.success("成功导入"+commodities.size()+"条数据", stringBuilder.toString());
    }

    @Override
    public List<Combination> getCombinations(Integer supId) {
        return commodityMapper.selectCombinations(supId);
    }

    @Override
    public Commodity getOneCommodity(QueryWrapper<Commodity> wrapper) {
        List<Commodity> commodities = commodityMapper.selectList(wrapper);
        if(commodities.size() > 1){
            throw new RuntimeException("查询到的商品过多，请输入更长的条码位数");
        }else if(commodities.isEmpty()){
            throw new RuntimeException("没有符合条件的商品");
        }else{
            return commodities.get(0);
        }
    }

    @Override
    public List<Combination> searchCombinations(Commodity commodity) {
        return commodityMapper.searchCombinations(commodity);
    }

    @Override
    public Page<Commodity> searchWarning(Page<Commodity> page, Integer supId, Integer claId, Integer supplierId,Integer funcId, Integer num) {
        return commodityMapper.searchWarning(page, supId, claId, supplierId, funcId, num);
    }

    @Override
    public List<Commodity> getCommodities(Commodity commodity) {
        return commodityMapper.selectCommodities(commodity);
    }

    @Override
    public List<Commodity> getCommoditiesByClaIds(List<Integer> claIds, Integer includeSonClass) {
        if(claIds.isEmpty()){
            return null;
        }else{
            return commodityMapper.selectCommoditiesByClaIds(claIds, includeSonClass);
        }
    }

    @Override
    @Transactional
    public boolean notIsDiscount(List<Commodity> commodities) {
        if(commodities.isEmpty())return true;
        try{
            for(Commodity c:commodities){
                c.setIsDiscount(!c.getIsDiscount());
                commodityMapper.updateIsDiscount(c);
            }
            return true;
        }catch (RuntimeException e){
            return false;
        }
    }

    @Override
    public List<Commodity> getNoDiscountCommodities(Integer supId) {
        return commodityMapper.selectNoDiscountCommodities(supId);
    }
}
