package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品相关属性
 * </p>
 *
 * @author harry
 * @since 2024-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Commodity对象", description="商品相关属性")
public class Commodity extends Model<Commodity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer supId;

    private String name;

    private String barcode;

    private String claId;

    private Double inventory;

    private Integer unitId;

    private Double purchasePrice;

    private Double price;

    private Double wholesalePrice;

    private String specification;

    private Boolean isDiscount;

    private Integer supplierId;

    private LocalDateTime produceDate;

    private Integer expirationTime;

    private Long parent;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDeleted;
    @TableField(exist = false)
    private Classification classification;
    @TableField(exist = false)
    private Unit unit;
    @TableField(exist = false)
    private Supplier supplier;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
