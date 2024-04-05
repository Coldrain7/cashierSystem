package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.model.domain.Supplier;
import com.example.mybatisplus.mapper.SupplierMapper;
import com.example.mybatisplus.model.dto.SortDTO;
import com.example.mybatisplus.service.SupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 供应商表 服务实现类
 * </p>
 *
 * @author harry
 * @since 2024-01-27
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;
    @Override
    public List<Supplier> getSupSuppliers(Integer supId) {
        QueryWrapper<Supplier> wrapper = new QueryWrapper<>();
        if(supId != null){
            wrapper.eq("sup_id", supId);
            return supplierMapper.selectList(wrapper);
        }else{
            return null;
        }
    }

    @Override
    public List<Supplier> getSuppliers(Supplier supplier, SortDTO sortDTO) {
        if(supplier.getSupId() != null){
            sortDTO.isSortable();
            return supplierMapper.getSuppliers(supplier, sortDTO);
        }else return null;
    }

    @Override
    public int insert(Supplier supplier) {
        supplierMapper.insert(supplier);
        return supplier.getId();
    }
}
