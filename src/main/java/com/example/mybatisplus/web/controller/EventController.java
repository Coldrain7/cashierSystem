package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.dto.PageDTO;
import org.apache.poi.ss.formula.functions.Even;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.EventService;
import com.example.mybatisplus.model.domain.Event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 前端控制器
 *
 * @author harry
 * @version v1.0
 * @since 2024-04-17
 */
@Controller
@RequestMapping("/api/event")
public class EventController {

    private final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    /**
     * 描述：根据Id 查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        Event event = eventService.getOneById(id);
        return JsonResponse.success(event);
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) {
        return JsonResponse.success(eventService.deleteEventById(id));
    }
    /**
     * 创建新活动,或者创建活动里的新商品
     * @param map 需要包含beginTime, endTime, comIds, prices, eventPrices;name可以为空
     * @return data: true if success, else false
     */
    @ResponseBody
    @PostMapping("/createEvent")
    public JsonResponse create(@RequestBody Map<String, Object> map) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (map.get("beginTime") == null || map.get("beginTime").equals("") || map.get("endTime") == null || map.get("endTime").equals("")) {
            return JsonResponse.failure("没有时间信息");
        } else {
                List<Object> comIds = (ArrayList<Object>) map.get("comIds");
                // 使用comIds...
                List<Object> prices = (ArrayList<Object>) map.get("prices");
                List<Object> eventPrices = (ArrayList<Object>) map.get("eventPrices");
                Event event = new Event();
                Long id;
                if(map.get("id") == null || Long.parseLong(map.get("id").toString()) <= 0){
                    id = IdWorker.getId(event);
                }else{
                    id = Long.parseLong(map.get("id").toString());
                }
                event.setId(id);
                event.setName(map.get("name") == null ? "" : map.get("name").toString());
                event.setBeginTime(LocalDate.parse(map.get("beginTime").toString(), df));
                event.setEndTime(LocalDate.parse(map.get("endTime").toString(), df));
                List<Event> events = new ArrayList<>();
                for (int i = 0; i < comIds.size(); i++) {
                    Event e = new Event();
                    e.setId(event.getId());
                    e.setName(event.getName());
                    e.setBeginTime(event.getBeginTime());
                    e.setEndTime(event.getEndTime());
                    e.setComId(Long.parseLong(comIds.get(i).toString()));
                    e.setPrice(Double.parseDouble(prices.get(i).toString()));
                    e.setEventPrice(Double.parseDouble(eventPrices.get(i).toString()));
                    events.add(e);
                }
                boolean res = eventService.addEvents(events);
                if(res){
                    return JsonResponse.success(id);
                }else{
                    return JsonResponse.failure("插入失败");
                }
        }
    }

    /**
     * 根据店铺id查询所有的营销活动
     *
     * @param supId 店铺id
     * @return 活动列表
     */
    @ResponseBody
    @GetMapping("/getEvents")
    public JsonResponse getEvents(PageDTO pageDTO, String name, Integer supId) {
        if (supId == null) {
            return JsonResponse.failure("没有店铺id信息");
        } else {
            Page<Event> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
            return JsonResponse.success(eventService.getEvents(page, name, supId));
        }
    }

    /**
     * 更新活动
     * @param map 需要包含id,beginTime, endTime, comIds, prices, eventPrices;name可以为空
     * @return id
     */
    @ResponseBody
    @PostMapping("/updateEvents")
    @Transactional
    public JsonResponse updateEvents(@RequestBody Map<String, Object>map){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (map.get("beginTime") == null || map.get("beginTime").equals("") || map.get("endTime") == null || map.get("endTime").equals("")) {
            return JsonResponse.failure("没有时间信息");
        } else {
            try{
                List<Object> comIds = (ArrayList<Object>) map.get("comIds");
                List<Object> eventPrices = (ArrayList<Object>) map.get("eventPrices");
                List<Boolean> isDeletedList = (ArrayList<Boolean>) map.get("isDeleted");
                String name =  map.get("name") == null ? "" : map.get("name").toString();
                LocalDate beginDate = LocalDate.parse(map.get("beginTime").toString(), df);
                LocalDate endDate = LocalDate.parse(map.get("endTime").toString(), df);
                Long id = Long.parseLong(map.get("id").toString());
                for(int i=0;i<comIds.size();i++){
                    UpdateWrapper<Event> wrapper = new UpdateWrapper<>();
                    wrapper.eq("id", id);
                    wrapper.set("name", name);
                    wrapper.set("begin_time",beginDate);
                    wrapper.set("end_time", endDate);
                    wrapper.eq("com_id", Long.parseLong(comIds.get(i).toString()));
                    wrapper.set("event_price", Double.parseDouble(eventPrices.get(i).toString()));
                    wrapper.set("is_deleted", isDeletedList.get(i));
                    eventService.update(wrapper);
                }
                return JsonResponse.success(id);
            }catch (RuntimeException e){
                e.printStackTrace();
                return JsonResponse.failure("保存失败");
            }
        }
    }


    /**
     * 根据活动id查询活动中的商品
     * @param id 活动id
     * @return 商品信息就用Event类存储，返回Event列表
     */
    @ResponseBody
    @GetMapping("/getEventCommodities/{id}")
    public JsonResponse getEventCommodities(@PathVariable("id")Long id){
        if(id == null) return JsonResponse.failure("查询失败：没有id信息");
        return JsonResponse.success(eventService.getEventCommodities(id));
    }

}

