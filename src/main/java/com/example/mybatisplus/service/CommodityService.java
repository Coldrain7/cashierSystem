package com.example.mybatisplus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Commodity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.model.dto.PageDTO;

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

}
