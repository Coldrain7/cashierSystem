package com.example.mybatisplus.model.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RecordVO {
    private Double totalNumber;//总数量
    private Double totalPayment;//总收款金额
    private Integer recordNum;//订单数量
    private Double totalPurchasePrice;//总进价
    private LocalDate beginDate;//
    private LocalDate endDate;
    private Double totalProfit;//总利润
    private Double profitRatio;//利润率
    private String week;//记录第几周
}
