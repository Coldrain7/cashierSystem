package com.example.mybatisplus.model.dto;

import lombok.Data;

@Data
public class UserInfoDTO {
    //用户表的子字段
    private Integer id;
    private String name;
    private Integer userType;
}
