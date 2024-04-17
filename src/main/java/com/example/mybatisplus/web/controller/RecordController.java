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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
     * 根据id删除
     * @param id record的id
     * @return 删除成功返回true,否则返回false
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id){
        return JsonResponse.success(recordService.removeById(id));
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
    @PostMapping("/createRecords")
    public JsonResponse createRecords(@RequestBody Map<String, Object> map){
        //commodityDTO里包含barcode,name,id,price,nowPrice,num,sum的信息
        // 从Map中提取data数组
        List<?> dataList = (List<?>) map.get("data");
        List<CommodityDTO> commodityDTOList = new ArrayList<>();
        Record record = new Record();
        Long id = IdWorker.getId(record);//生成一个雪花算法的id
        record.setId(id);
        record.setMethod(Integer.parseInt(map.get("method").toString()));
        record.setWorkerId(Integer.parseInt(map.get("workerId").toString()));
        if(map.get("memId")!=null && StringUtils.isNotBlank(map.get("memId").toString())){
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
        return JsonResponse.success(recordService.createRecords(records));
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

    /**
     * 根据supId获取店铺的所有销售单据
     * @param pageDTO 分页数据
     * @param supId 店铺id
     * @param record 存储type信息，如果还包含workerId信息的话，会对workerId字段进行模糊查询
     * @return 返回Page封装的Record
     */
    @ResponseBody
    @GetMapping("/getSupRecords/{supId}")
    public JsonResponse getSupRecords(PageDTO pageDTO, @PathVariable("supId")Integer supId, Record record){
        if(supId == null)return JsonResponse.success(null);
        Page<Record> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        return JsonResponse.success(recordService.getRecords(page, supId, record));
    }
    /**
     * 查询销售单据详情信息
     * @param record 必须包含id,type，根据这两个字段查询
     * @return 没有订单号data返回null，否则data返回查询到的数据
     */
    @ResponseBody
    @GetMapping("/getRecordWithCommodity")
    public JsonResponse getRecordWihCommodity(Record record){
        if(record.getId() == null){
            return JsonResponse.success(null);
        }else{
            return JsonResponse.success(recordService.getRecordWithCommodity(record));
        }
    }

    /**
     * 根据店铺id获取所有的挂单
     * @param supId 店铺id
     * @return 查询到的Record列表或null
     */
    @ResponseBody
    @GetMapping("/getPendedRecords/{id}")
    public JsonResponse getPendedRecords(@PathVariable("id") Integer supId){
        if(supId != null){
            return JsonResponse.success(recordService.getPendedRecords(supId));
        }else{
            return JsonResponse.success(null);
        }
    }

    /**
     * 根据挂单获取商品信息
     * @param record 需要包含id信息，不对type进行检验
     * @return 返回CommodityDTO的列表或null
     */
    @ResponseBody
    @GetMapping("/getPendedCommodities")
    public JsonResponse getPendedCommodities(Record record){
        if(record.getId() == null){
            return JsonResponse.success(null);
        }else{
            return JsonResponse.success(recordService.getPendedCommodities(record));
        }
    }

    /**
     * 按年、月、周查询销售数据信息
     * @param map 必须包含beginDate, endDate,mode与supId信息
     * @return recordVO列表,按天查询日期放在beginDate中；按月查询会把这个月的最小日期放入beginDate,前端此时需要修改下格式
     */
    @ResponseBody
    @PostMapping("/getSale")
    public JsonResponse getSale(@RequestBody Map<String, Object> map){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(map.get("beginDate") == null || map.get("beginDate").equals("")||map.get("endDate") == null || map.get("endDate").equals("")){
            return JsonResponse.failure("没有时间信息");
        }else{
            LocalDate beginDate = LocalDate.parse(map.get("beginDate").toString(), df);
            LocalDate endDate = LocalDate.parse(map.get("endDate").toString(), df);
            Integer mode = Integer.parseInt(map.get("mode").toString());
            Integer supId = Integer.parseInt(map.get("supId").toString());
            return JsonResponse.success(recordService.getSale(beginDate, endDate, mode, supId));
        }
    }

    /**
     * 根据时间查询商品销售占比信息
     * @param map 必须包含beginDate, endDate,includeSupClass与supId信息
     * @return ProportionVO列表，需要前端处理
     */
    @ResponseBody
    @PostMapping("/getProportion")
    public JsonResponse getProportion(@RequestBody Map<String, Object>map){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(map.get("beginDate") == null || map.get("beginDate").equals("")||map.get("endDate") == null || map.get("endDate").equals("")){
            return JsonResponse.failure("没有时间信息");
        }else{
            try{
                LocalDate beginDate = LocalDate.parse(map.get("beginDate").toString(), df);
                LocalDate endDate = LocalDate.parse(map.get("endDate").toString(), df);
                Boolean includeSubClass = Boolean.parseBoolean(map.get("includeSubClass").toString());
                Integer supId = Integer.parseInt(map.get("supId").toString());
                return JsonResponse.success(recordService.getProportion(beginDate, endDate, includeSubClass, supId));
            }catch (RuntimeException e){
                return JsonResponse.failure("查询失败");
            }
        }
    }

    /**
     * 根据数据预测后面的值
     * @param sellData Double列表
     * @return 数据长度足够返回预测值的Double列表，否则返回错误信息
     */
    @ResponseBody
    @PostMapping("/predictSelling")
    public JsonResponse predictSelling(@RequestBody List<Double> sellData){
        if(sellData.size() < 3){
            return JsonResponse.failure("数据过少");
        }else{
            //默认传给前端3个预测值
            return JsonResponse.success(recordService.predictSelling(sellData, 3));
        }
    }

    /**
     * 根据商品id按周查询商品销售记录
     * @param map 包含beginDate, endDate, comId
     * @return RecordVO列表
     */
    @ResponseBody
    @PostMapping("/getSellingByComId")
    public JsonResponse getSellingByComId(@RequestBody Map<String, Object>map){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(map.get("beginDate") == null || map.get("beginDate").equals("")||map.get("endDate") == null || map.get("endDate").equals("")){
            return JsonResponse.failure("没有时间信息");
        }else{
            try{
                LocalDate beginDate = LocalDate.parse(map.get("beginDate").toString(), df);
                LocalDate endDate = LocalDate.parse(map.get("endDate").toString(), df);
                Long comId = Long.parseLong(map.get("comId").toString());
                return JsonResponse.success(recordService.getSellingByComId(beginDate, endDate, comId));
            }catch (RuntimeException e){
                e.printStackTrace();
                return JsonResponse.failure("查询失败");
            }
        }
    }
}

