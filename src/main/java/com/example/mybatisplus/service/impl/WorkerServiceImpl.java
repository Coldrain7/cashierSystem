package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.model.domain.Worker;
import com.example.mybatisplus.mapper.WorkerMapper;
import com.example.mybatisplus.service.WorkerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工，包含收银员，管理员 服务实现类
 * </p>
 *
 * @author harry
 * @since 2024-01-06
 */
@Service
public class WorkerServiceImpl extends ServiceImpl<WorkerMapper, Worker> implements WorkerService {
    @Autowired
    private WorkerMapper workerMapper;
    @Override
    public Worker login(Worker worker) {
        QueryWrapper<Worker> wrapper = new QueryWrapper<>();
        wrapper.eq("id", worker.getId()).eq("password", worker.getPassword());
        worker = workerMapper.selectOne(wrapper);
        return worker;
    }

    @Override
    public Integer insert(Worker worker) {
        workerMapper.insert(worker);
        return worker.getId();
    }
}
