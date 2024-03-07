package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.model.domain.Classification;
import com.example.mybatisplus.mapper.ClassificationMapper;
import com.example.mybatisplus.service.ClassificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private ClassificationMapper classificationMapper;
    @Override
    public List<Classification> getSupClassifications(Integer supId) {
        QueryWrapper<Classification> wrapper = new QueryWrapper<>();
        if(supId != null){
            wrapper.eq("sup_id", supId);
            return classificationMapper.selectList(wrapper);
        }else{
            return null;
        }
    }

    @Override
    public int insert(Classification classification) {
        classificationMapper.insert(classification);
        return classification.getId();
    }

    @Override
    public List<Classification> searchClassifications(Classification classification) {
        return classificationMapper.searchClassifications(classification);
    }
}
