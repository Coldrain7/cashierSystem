package com.example.mybatisplus.model.dto;

import lombok.Data;

@Data
public class CommodityDTO {
    private Long id;
    private Double num;
    private Double sum;//对应前端小计
    private String barcode;
    private String name;
    private Double price;
}
