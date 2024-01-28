package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Classification;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 存储分类的结构，树形结构 服务类
 * </p>
 *
 * @author harry
 * @since 2024-01-19
 */
public interface ClassificationService extends IService<Classification> {

    List<Classification> getSupClassifications(Integer supId);
}
