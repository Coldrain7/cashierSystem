package com.example.mybatisplus.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.model.domain.Combination;
import com.example.mybatisplus.model.domain.Commodity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.model.dto.SortDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品相关属性 服务类
 * </p>
 *
 * @author harry
 * @since 2024-01-06
 */
public interface CommodityService extends IService<Commodity> {

    Page<Commodity> commodityPage(PageDTO pageDTO, Commodity commodity);
    Page<Commodity> commodityPageInOrder(PageDTO pageDTO, Commodity commodity, SortDTO sortDTO);

    long insert(Commodity commodity);

    /**
     * 条形码需要包装成Commodity类，但只需要Commodity的barcode属性
     * @param commodity 包含条形码barcode属性
     * @return 条形码已存在就返回commodity类
     */
    Commodity getUniqueBarcode(Commodity commodity);

    Page<Commodity> searchCommodities(PageDTO pageDTO, Commodity commodity, SortDTO sortDTO);

    Page<Commodity> advanceSearch(Page<Commodity> page, ArrayList<Integer> claIds, ArrayList<Integer> supplierIds,
                                  Commodity commodity, Commodity secondCommodity, SortDTO sortDTO);

    void exportCommodities(HttpServletResponse httpServletResponse, Commodity commodity) throws IOException;


    void exportCommodities(HttpServletResponse httpServletResponse, ArrayList<Integer> claIds,
                           ArrayList<Integer> supplierIds, Commodity commodity, Commodity secondCommodity)
            throws IOException;

    void exportTemplate(HttpServletResponse httpServletResponse);

    JsonResponse importCommodities(List<String[]> strings, int supId);

    List<Combination> getCombinations(Integer supId);

    Commodity getOneCommodity(QueryWrapper<Commodity> wrapper);

    List<Combination> searchCombinations(Commodity commodity);

    Page<Commodity> searchWarning(Page<Commodity> page,Integer supId, Integer claId, Integer supplierId,Integer funcId, Integer num);

    List<Commodity> getCommodities(Commodity commodity);

    List<Commodity> getCommoditiesByClaIds(List<Integer> claIds, Integer includeSonClass);

    boolean notIsDiscount(List<Commodity> commodities);

    List<Commodity> getNoDiscountCommodities(Integer supId);
    void updateCombinationInventory();
}
