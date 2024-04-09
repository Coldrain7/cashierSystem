package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 销售记录 Mapper 接口
 * </p>
 *
 * @author harry
 * @since 2024-01-06
 */
public interface RecordMapper extends BaseMapper<Record> {

    boolean insertRecords(@Param("records") List<Record> records);
}
