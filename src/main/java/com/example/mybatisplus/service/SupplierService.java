package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Supplier;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.model.dto.SortDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 供应商表 服务类
 * </p>
 *
 * @author harry
 * @since 2024-01-27
 */
public interface SupplierService extends IService<Supplier> {

    List<Supplier> getSupSuppliers(Integer supId);

    List<Supplier> getSuppliers(Supplier supplier, SortDTO sortDTO);

    int insert(Supplier supplier);

    void exportSuppliers(HttpServletResponse httpServletResponse, Supplier supplier);
}
