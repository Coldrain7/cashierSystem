package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Supplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.dto.SupplierDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 供应商表 Mapper 接口
 * </p>
 *
 * @author harry
 * @since 2024-01-27
 */
@Repository
public interface SupplierMapper extends BaseMapper<Supplier> {
    List<SupplierDTO> getSupplierList(@Param("supId")int supId);
}
