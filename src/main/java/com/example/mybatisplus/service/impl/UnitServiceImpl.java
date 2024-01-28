package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.model.domain.Unit;
import com.example.mybatisplus.mapper.UnitMapper;
import com.example.mybatisplus.service.UnitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.List;

/**
 * <p>
 * 单位表 服务实现类
 * </p>
 *
 * @author harry
 * @since 2024-01-25
 */
@Service
public class UnitServiceImpl extends ServiceImpl<UnitMapper, Unit> implements UnitService {
    @Autowired
    private UnitMapper unitMapper;

    @Override
    public List<Unit> getSupUnits(Integer supId) {
        QueryWrapper<Unit> wrapper = new QueryWrapper<>();
        if(supId != null){
            wrapper.eq("sup_id", supId);
            return unitMapper.selectList(wrapper);
        }else{
            return null;
        }
    }
}
