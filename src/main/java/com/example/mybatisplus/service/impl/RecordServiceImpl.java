package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.mapper.CommodityMapper;
import com.example.mybatisplus.model.domain.Record;
import com.example.mybatisplus.mapper.RecordMapper;
import com.example.mybatisplus.model.dto.CommodityDTO;
import com.example.mybatisplus.model.vo.ProportionVO;
import com.example.mybatisplus.model.vo.RecordVO;
import com.example.mybatisplus.service.RecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
            if (records.get(0).getType() != 2) {
                for (Record record : records) {
                    commodityMapper.updateInventory(record);
                }
            }
            return recordMapper.insertRecords(records);
        } catch (RuntimeException e) {
            e.printStackTrace();
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
    public List<RecordVO> getSale(LocalDate beginDate, LocalDate endDate, Integer mode, Integer supId) {
        return recordMapper.selectSale(beginDate, endDate, mode, supId);
    }

    @Override
    public List<ProportionVO> getProportion(LocalDate beginDate, LocalDate endDate, Boolean includeSubClass, Integer supId) {
        return recordMapper.selectProportion(beginDate, endDate, includeSubClass, supId);
    }

    private void indexSmooth(double[] s0, double[] s1, int n, double alpha) {
        for (int i = 1; i < n; i++) {
            s1[i] = alpha * s0[i] + (1 - alpha) * s1[i - 1];
        }
    }

    /**
     * 用指数平滑法预测值
     *
     * @param sellData 原始数据
     * @param n        预测数据的个数
     * @return 预测值列表
     */
    @Override
    public List<Double> predictSelling(List<Double> sellData, int n) {
        if (sellData.size() < n) return null;
        double alpha = 0.3;
        int length = sellData.size() + 1;
        double[] s1 = new double[length];
        double[] s2 = new double[length];
        double[] s3 = new double[length];
        s1[0] = (sellData.get(0) + sellData.get(1) + sellData.get(2)) / 3.0;
        s2[0] = s1[0];
        s3[0] = 0.75 * sellData.get(1) + 0.25 * sellData.get(0);
        double[] y = new double[length];
        for(int i=1;i< length;i++){
            y[i] = sellData.get(i-1);
        }
        indexSmooth(y, s1, length, alpha);
        indexSmooth(s1, s2, length, alpha);
        indexSmooth(s2, s3, length, alpha);
        int t = length - 1;
        double a = 3 * (s1[t] - s2[t]) + s3[t];
        double coefficient = alpha/(2 * (1-alpha) * (1-alpha));
        double b = coefficient * ((6-5*alpha)*s1[t] - 2*(5-4*alpha)*s2[t] + (4-3*alpha)*s3[t]);
        double c = alpha*coefficient*(s1[t] - 2*s2[t]+s3[t]);
        List<Double> results = new ArrayList<>();
        for(int i=1;i<=n;i++){
            results.add(a + b * i + c * i*i);
        }
        return results;
    }

    @Override
    public List<RecordVO> getSellingByComId(LocalDate beginDate, LocalDate endDate, Long comId) {
        return recordMapper.selectSellingByComId(beginDate, endDate, comId);
    }

    @Override
    public Page<Record> getRecords(Page<Record> page, Integer supId, Record record) {
        return recordMapper.selectSupRecords(page, supId, record);
    }

}
