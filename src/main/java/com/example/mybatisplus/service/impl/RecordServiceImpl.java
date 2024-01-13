package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Record;
import com.example.mybatisplus.mapper.RecordMapper;
import com.example.mybatisplus.service.RecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 销售记录 服务实现类
 * </p>
 *
 * @author harry
 * @since 2024-01-06
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

}
