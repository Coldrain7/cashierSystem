package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Worker;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 员工，包含收银员，管理员 服务类
 * </p>
 *
 * @author harry
 * @since 2024-01-06
 */
public interface WorkerService extends IService<Worker> {

    Worker login(Worker worker);
    Integer insert(Worker worker);

    List<Worker> getSupWorkers(Integer supId);

    List<Worker> getWorkersByName(Worker worker);

    boolean updateWorkerById(Worker worker);
}
