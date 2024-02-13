package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Commodity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.dto.PageDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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

    Commodity getUniqueBarcode(@Param("commodity") Commodity commodity);
}
