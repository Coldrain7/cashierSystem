package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Unit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.dto.UnitDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 单位表 Mapper 接口
 * </p>
 *
 * @author harry
 * @since 2024-01-25
 */
@Repository
public interface UnitMapper extends BaseMapper<Unit> {



    List<UnitDTO> getUnitList(@Param("supId") int supId);
}
