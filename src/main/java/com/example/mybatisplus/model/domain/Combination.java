package com.example.mybatisplus.model.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="组合拆分对象", description="用于存储组合拆分的两个商品信息")
public class Combination {
    private static final long serialVersionUID = 1L;

    private Integer supId;

    private Long parentId;

    private String parentName;

    private String parentBarcode;

    private Double parentPurchasePrice;

    private String childSpecification;

    private Long childId;

    private String childName;

    private String childBarcode;

    private Double childPurchasePrice;

    private Long parent;
}
