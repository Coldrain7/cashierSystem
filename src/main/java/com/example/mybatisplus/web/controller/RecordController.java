package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.dto.CommodityDTO;
import com.example.mybatisplus.model.dto.PageDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.RecordService;
import com.example.mybatisplus.model.domain.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 *  前端控制器
 *
 *
 * @author harry
 * @since 2024-01-06
 * @version v1.0
 */
@Controller
@RequestMapping("/api/record")
public class RecordController {

    private final Logger logger = LoggerFactory.getLogger( RecordController.class );

    @Autowired
    private RecordService recordService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Record  record =  recordService.getById(id);
        return JsonResponse.success(record);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        recordService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateRecord(Record  record) throws Exception {
        recordService.updateById(record);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Record
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Record  record) throws Exception {
        recordService.save(record);
        return JsonResponse.success(null);
    }

    /**
     * 售出商品时调用的api，会更新commodity表的库存并在record表插入数据
     * @param map 前端包装好的信息
     * @return data返回true如果对数据库操作成功，否则返回false
     */
    @ResponseBody
    @PostMapping("/sellCommodities")
    public JsonResponse sellCommodities(@RequestBody Map<String, Object> map){
        //commodityDTO里包含barcode,name,id,price,nowPrice,num,sum的信息
        // 从Map中提取data数组
        List<?> dataList = (List<?>) map.get("data");
        List<CommodityDTO> commodityDTOList = new ArrayList<>();
        Record record = new Record();
        Long id = IdWorker.getId(record);//生成一个雪花算法的id
        record.setId(id);
        record.setMethod(Integer.parseInt(map.get("method").toString()));
        record.setWorkerId(Integer.parseInt(map.get("workerId").toString()));
        if(StringUtils.isNotBlank(map.get("memId").toString())){
            record.setMemId(Integer.parseInt(map.get("memId").toString()));
        }
        record.setType(Integer.parseInt(map.get("type").toString()));
        int rowSize = Integer.parseInt(map.get("rowSize").toString());
        List<Record> records = new ArrayList<>();
        // 前端的rowSize代表有多少行商品，所以小于rowSize的下标都是有效数据
        for (int i=0;i<rowSize;i++) {//Object obj : dataList
            // 将obj转换为Map，因为Jackson可能无法直接将Object转换为DTO
            Map<?, ?> itemMap = (Map<?, ?>) dataList.get(i);
            // 创建DTO实例并设置属性
            CommodityDTO commodityDTO = new CommodityDTO();
            commodityDTO.setId(Long.parseLong(itemMap.get("id").toString()));
            commodityDTO.setNum(Double.parseDouble(itemMap.get("num").toString()));
            commodityDTO.setSum(Double.parseDouble(itemMap.get("sum").toString()));
            // 将DTO添加到列表
            commodityDTOList.add(commodityDTO);
        }
        // 已用rowSize确保commodityDTOList有效，不需要再在循环内判断数据是否为空
        for (CommodityDTO commodityDTO : commodityDTOList) {
            Record r = new Record();
            r.setId(record.getId());
            r.setMethod(record.getMethod());
            r.setWorkerId(record.getWorkerId());
            r.setMemId(record.getMemId());
            r.setType(record.getType());
            r.setComId(commodityDTO.getId());
            r.setPayment(commodityDTO.getSum());
            r.setNumber(commodityDTO.getNum());
            records.add(r);
        }
        return JsonResponse.success(recordService.sellCommodities(records));
    }

    /**
     * 收银员销售单据页面查询单据信息
     * @param pageDTO 分页信息
     * @param record 必须包含workerId
     * @return 没有workerId返回data返回null,否则返回查询到的数据
     */
    @ResponseBody
    @GetMapping("/getRecordsWithMember")
    public JsonResponse getRecordsWithMember(PageDTO pageDTO, Record record){
        if(record.getWorkerId() == null) return JsonResponse.success(null);
        Page<Record> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        return JsonResponse.success(recordService.getRecords(page, record));
    }
    @ResponseBody
    @GetMapping("/getRecordWithCommodity")
    public JsonResponse getRecordWihCommodity(Record record){
        if(record.getId() == null){
            return JsonResponse.success(null);
        }else{
            return JsonResponse.success(recordService.getRecordWithCommodity(record));
        }
    }
}

