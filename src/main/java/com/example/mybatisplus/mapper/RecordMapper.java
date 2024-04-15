package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.dto.CommodityDTO;
import com.example.mybatisplus.model.vo.recordVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
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

    /**
     * 使用Record类原有的payment字段来存储总价钱，查询的信息还包括member表的name字段
     * @param page 分页器
     * @param record 需要包含workerId
     * @return 查询到的单据信息
     */
    Page<Record> selectRecordsWithMember(Page<Record> page, @Param("record")Record record);

    List<Record> selectRecordWithCommodity(@Param("record")Record record);

    List<Record> selectPendedRecords(@Param("supId") Integer supId);

    List<CommodityDTO> selectCommodityDTOByRecord(@Param("record") Record record);

    List<recordVO> selectSale(@Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate,
                              @Param("mode") Integer mode, @Param("supId")Integer supId);
}
