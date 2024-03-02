package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.utls.ExcelUtils;
import com.example.mybatisplus.model.domain.Book;
import com.example.mybatisplus.model.domain.Commodity;
import com.example.mybatisplus.mapper.CommodityMapper;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.model.dto.SortDTO;
import com.example.mybatisplus.service.CommodityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
}
