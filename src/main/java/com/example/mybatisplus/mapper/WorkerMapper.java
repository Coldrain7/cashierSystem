package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Worker;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 员工，包含收银员，管理员 Mapper 接口
 * </p>
 *
 * @author harry
 * @since 2024-01-06
 */
@Repository
public interface WorkerMapper extends BaseMapper<Worker> {

}
