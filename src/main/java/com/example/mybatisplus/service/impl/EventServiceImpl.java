package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Commodity;
import com.example.mybatisplus.model.domain.Event;
import com.example.mybatisplus.mapper.EventMapper;
import com.example.mybatisplus.model.domain.Record;
import com.example.mybatisplus.service.CommodityService;
import com.example.mybatisplus.service.EventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.Even;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author harry
 * @since 2024-04-17
 */
@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {
    @Autowired
    private EventMapper eventMapper; // 假设你有一个MyBatis Plus的Mapper接口

    @Autowired
    private CommodityService commodityService; // 假设还有一个处理Commodity的Service

    @Transactional
    public void updateCommodityPricesBasedOnEvents() {
        List<Event> activeEvents = eventMapper.selectActiveEvents(); // 假设你有一个查询活跃事件的Mapper方法
        for (Event event : activeEvents) {
            // 获取event对应的commodity ID
            Long commodityId = event.getComId();
            // 调用CommodityService的方法来更新price
            UpdateWrapper<Commodity> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", commodityId).set("price", event.getEventPrice());
            commodityService.update(wrapper);
        }
        List<Event> endEvents = eventMapper.selectEndEvent();
        for (Event event : endEvents) {
            // 获取event对应的commodity ID
            Long commodityId = event.getComId();
            // 调用CommodityService的方法来更新price
            UpdateWrapper<Commodity> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", commodityId).set("price", event.getPrice());
            commodityService.update(wrapper);
            UpdateWrapper<Event> eventUpdateWrapper = new UpdateWrapper<>();
            eventUpdateWrapper.eq("id", event.getId()).set("is_done", true);
            eventMapper.update(null,eventUpdateWrapper);
        }
    }

    @Override
    public Page<Event> getEvents(Page<Event> page, String name,  Integer supId) {
        return eventMapper.selectEvent(page, name,  supId);
    }

    @Override
    @Transactional
    public boolean addEvents(List<Event> events) {
        try {
            return eventMapper.insertEvents(events);
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("sql插入event错误");
            return false;
        }
    }

    @Override
    public List<Event> getEventCommodities(Long id) {
        return eventMapper.selectEventCommodities(id);
    }

    @Override
    public Event getOneById(Long id) {
        return eventMapper.selectOneEvent(id);
    }

    @Override
    public boolean deleteEventById(Long id) {
        return eventMapper.deleteEventById(id);
    }

}
