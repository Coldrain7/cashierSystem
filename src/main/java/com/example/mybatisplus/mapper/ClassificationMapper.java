package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Classification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 存储分类的结构，树形结构 Mapper 接口
 * </p>
 *
 * @author harry
 * @since 2024-01-19
 */
@Repository
public interface ClassificationMapper extends BaseMapper<Classification> {

}
