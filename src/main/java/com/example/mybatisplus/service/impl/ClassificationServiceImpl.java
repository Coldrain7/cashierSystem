package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Classification;
import com.example.mybatisplus.mapper.ClassificationMapper;
import com.example.mybatisplus.service.ClassificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 存储分类的结构，树形结构 服务实现类
 * </p>
 *
 * @author harry
 * @since 2024-01-19
 */
@Service
public class ClassificationServiceImpl extends ServiceImpl<ClassificationMapper, Classification> implements ClassificationService {

}
