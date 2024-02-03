package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Book;
import com.example.mybatisplus.model.domain.Commodity;
import com.example.mybatisplus.mapper.CommodityMapper;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.service.CommodityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public long insert(Commodity commodity) {
        commodityMapper.insert(commodity);
        return commodity.getId();
    }
}
