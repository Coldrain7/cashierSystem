package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Supermarket;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author harry
 * @since 2024-01-07
 */
public interface SupermarketService extends IService<Supermarket> {
    Integer register(Supermarket supermarket);
}
