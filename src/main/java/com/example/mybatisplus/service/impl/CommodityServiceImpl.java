package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Commodity;
import com.example.mybatisplus.mapper.CommodityMapper;
import com.example.mybatisplus.service.CommodityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
