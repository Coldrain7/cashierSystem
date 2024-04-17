package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Event;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author harry
 * @since 2024-04-17
 */
public interface EventService extends IService<Event> {

    void updateCommodityPricesBasedOnEvents();

    List<Event> getAllEvents(Integer supId);
}
