package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Supermarket;
import com.example.mybatisplus.mapper.SupermarketMapper;
import com.example.mybatisplus.service.SupermarketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author harry
 * @since 2024-01-07
 */
@Service
public class SupermarketServiceImpl extends ServiceImpl<SupermarketMapper, Supermarket> implements SupermarketService {

    @Autowired
    private SupermarketMapper supermarketMapper;
    @Override
    public Integer register(Supermarket supermarket) {
        supermarketMapper.insert(supermarket);
        return supermarket.getId();
    }
}
