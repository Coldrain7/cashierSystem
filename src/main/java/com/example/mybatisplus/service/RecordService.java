package com.example.mybatisplus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.model.domain.Commodity;
import com.example.mybatisplus.model.domain.Record;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.model.dto.CommodityDTO;
import com.example.mybatisplus.model.vo.ProportionVO;
import com.example.mybatisplus.model.vo.recordVO;

import java.time.LocalDate;
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

    boolean createRecords(List<Record> records);

    Page<Record> getRecords(Page<Record> page, Record record);
    Page<Record> getRecords(Page<Record> page, Integer supId, Record record);

    List<Record> getRecordWithCommodity(Record record);

    List<Record> getPendedRecords(Integer supId);

    List<CommodityDTO> getPendedCommodities(Record record);

    List<recordVO> getSale(LocalDate beginDate, LocalDate endDate, Integer mode, Integer supId);

    List<ProportionVO> getProportion(LocalDate beginDate, LocalDate endDate, Boolean includeSubClass, Integer supId);

}
