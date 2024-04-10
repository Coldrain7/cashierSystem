package com.example.mybatisplus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Commodity;
import com.example.mybatisplus.model.domain.Record;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 销售记录 服务类
 * </p>
 *
 * @author harry
 * @since 2024-01-06
 */
public interface RecordService extends IService<Record> {


    boolean insertRecords(List<Record> records);

    boolean sellCommodities(List<Record> records);

    Page<Record> getRecords(Page<Record> page, Record record);

    List<Record> getRecordWithCommodity(Record record);
}
