package com.example.mybatisplus.mapper;

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

    List<Event> selectEventBySupId(@Param("supId") Integer supId);
}
