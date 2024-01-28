package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Unit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 单位表 服务类
 * </p>
 *
 * @author harry
 * @since 2024-01-25
 */
public interface UnitService extends IService<Unit> {

    List<Unit> getSupUnits(Integer supId);
}
