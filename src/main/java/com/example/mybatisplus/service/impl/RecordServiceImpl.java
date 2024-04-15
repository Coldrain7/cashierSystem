package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.mapper.CommodityMapper;
import com.example.mybatisplus.model.domain.Record;
import com.example.mybatisplus.mapper.RecordMapper;
import com.example.mybatisplus.model.dto.CommodityDTO;
import com.example.mybatisplus.model.vo.recordVO;
import com.example.mybatisplus.service.RecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private CommodityMapper commodityMapper;

    @Override
    @Transactional
    public boolean insertRecords(List<Record> records) {
        return recordMapper.insertRecords(records);
    }

    @Override
    @Transactional
    public boolean createRecords(List<Record> records) {
        try {
            if(records.get(0).getType() != 2){
                for(Record record: records){
                    commodityMapper.updateInventory(record);
                }
            }
            return recordMapper.insertRecords(records);
        }catch (RuntimeException e){
            //e.printStackTrace();
            System.out.println("sql查询错误");
            return false;
        }
    }

    @Override
    public Page<Record> getRecords(Page<Record> page, Record record) {
        return recordMapper.selectRecordsWithMember(page, record);
    }

    @Override
    public List<Record> getRecordWithCommodity(Record record) {
        return recordMapper.selectRecordWithCommodity(record);
    }

    @Override
    public List<Record> getPendedRecords(Integer supId) {
        return recordMapper.selectPendedRecords(supId);
    }

    @Override
    public List<CommodityDTO> getPendedCommodities(Record record) {
        return recordMapper.selectCommodityDTOByRecord(record);
    }

    @Override
    public List<recordVO> getSale(LocalDate beginDate, LocalDate endDate, Integer mode, Integer supId) {
        return recordMapper.selectSale(beginDate, endDate, mode, supId);
    }


}
