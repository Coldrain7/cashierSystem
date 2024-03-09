package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Combination;
import com.example.mybatisplus.model.domain.Commodity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.model.dto.SortDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品相关属性 Mapper 接口
 * </p>
 *
 * @author harry
 * @since 2024-01-06
 */
@Repository
public interface CommodityMapper extends BaseMapper<Commodity> {

    Page<Commodity> selectCommodityPage(Page page, @Param("commodity")Commodity commodity);


    /**
     * 不包含搜索功能只包含排序的查询
     * @param page 分页器
     * @param commodity 商品类
     * @param sortDTO 必须能够排序
     * @return Page
     */
    Page<Commodity> selectCommodityInOrder(Page page, @Param("commodity")Commodity commodity, @Param("sortDTO") SortDTO sortDTO);

    /**
     * 根据传入Commodity对象的name和barcode字段查询，根据sortDTO中的字段排序
     * @param page 分页器
     * @param commodity 需要包含name和barcode字段，不能为null或""
     * @param sortDTO order字段为asc或desc时才能排序
     * @return Page<Commodity>
     */
    Page<Commodity> searchCommodityPage(Page page, @Param("commodity") Commodity commodity, @Param("sortDTO")SortDTO sortDTO);
    /**
     * 根据传入Commodity对象的name和barcode字段查询，根据sortDTO中的字段排序
     * @param commodity 需要包含name和barcode字段，不能为null或""
     * @param sortDTO order字段为asc或desc时才能排序
     * @return 所有符合条件的商品，不分页
     */
    List<Commodity> searchCommodityPage(@Param("commodity") Commodity commodity, @Param("sortDTO")SortDTO sortDTO);

    /**
     * 关联commodity表和multibarcode表查询所属店铺条码是否已存在
     * @param commodity barcode字段和supId不能为空
     * @return 若条码已存在返回商品，否则返回空
     */
    Commodity getUniqueBarcode(@Param("commodity") Commodity commodity);


    Page<Commodity> advanceSearch(Page<Commodity> page, @Param("claIds")ArrayList<Integer> claIds,
                                  @Param("supplierIds")ArrayList<Integer> supplierIds, @Param("commodity")Commodity commodity,
                                  @Param("secondCommodity")Commodity secondCommodity, @Param("sortDTO") SortDTO sortDTO);
    List<Commodity> advanceSearch(@Param("claIds")ArrayList<Integer> claIds,
                                  @Param("supplierIds")ArrayList<Integer> supplierIds, @Param("commodity")Commodity commodity,
                                  @Param("secondCommodity")Commodity secondCommodity, @Param("sortDTO") SortDTO sortDTO);

    List<Combination> selectCombinations(@Param("supId") Integer supId);

    List<Combination> searchCombinations(@Param("commodity") Commodity commodity);
}
