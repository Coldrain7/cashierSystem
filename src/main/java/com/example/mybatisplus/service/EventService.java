package com.example.mybatisplus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Event;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
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

    Page<Event> getEvents(Page<Event> page, String name, Integer supId);

    boolean addEvents(List<Event> events);

    List<Event> getEventCommodities(Long id);

    Event getOneById(Long id);

    boolean deleteEventById(Long id);
}
