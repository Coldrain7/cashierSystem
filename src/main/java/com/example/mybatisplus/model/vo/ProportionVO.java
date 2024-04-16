package com.example.mybatisplus.model.vo;

import lombok.Data;

@Data
public class ProportionVO {
    private Double totalNumber;//总数量
    private Double totalPayment;//总收款金额
    private Double totalProfit;//总利润
    private String classification;//分类名
}
