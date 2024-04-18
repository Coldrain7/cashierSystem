package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Event;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author harry
 * @since 2024-04-17
 */
@Repository
public interface EventMapper extends BaseMapper<Event> {

    List<Event> selectActiveEvents();

    List<Event> selectEndEvent();

    Page<Event> selectEvent(Page<Event> page, @Param("name") String name,  @Param("supId") Integer supId);

    boolean insertEvents(@Param(("events")) List<Event> events);

    List<Event> selectEventCommodities(@Param("id") Long id);

    Event selectOneEvent(@Param("id") Long id);

    boolean deleteEventById(@Param("id") Long id);
}
