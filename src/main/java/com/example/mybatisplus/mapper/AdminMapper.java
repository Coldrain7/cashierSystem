package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lxp
 * @since 2021-06-17
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {
    Admin mySelectById(@Param("id") Long id);
    Admin mySelectByAdmin(@Param("admin")Admin admin);
    List<Admin> list();

    Page<Admin> pageList(@Param("page")Page<Admin> page);
}
