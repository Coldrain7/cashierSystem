package com.example.mybatisplus.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.EventService;
import com.example.mybatisplus.model.domain.Event;


/**
 *
 *  前端控制器
 *
 *
 * @author harry
 * @since 2024-04-17
 * @version v1.0
 */
@Controller
@RequestMapping("/api/event")
public class EventController {

    private final Logger logger = LoggerFactory.getLogger( EventController.class );

    @Autowired
    private EventService eventService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Event  event =  eventService.getById(id);
        return JsonResponse.success(event);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        eventService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateEvent(Event  event) throws Exception {
        eventService.updateById(event);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Event
    *
    */
    @ResponseBody
    @PostMapping("/createEvent")
    public JsonResponse create(Event  event) throws Exception {
        eventService.save(event);
        return JsonResponse.success(null);
    }

    /**
     * 根据店铺id查询所有的营销活动
     * @param supId 店铺id
     * @return 活动列表
     */
    @ResponseBody
    @GetMapping("/getAllEvents")
    public JsonResponse getAllEvents(Integer supId){
        if(supId == null){
            return JsonResponse.failure("没有店铺id信息");
        }else{
            return JsonResponse.success(eventService.getAllEvents(supId));
        }
    }
}

