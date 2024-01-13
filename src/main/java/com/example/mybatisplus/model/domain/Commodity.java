package com.example.mybatisplus.model.domain;

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

    @TableId("id")
    private Integer id;

    private Integer worId;

    private Integer supId;

    private String name;

    private String barcode;

    private String classification;

    private Double inventory;

    private String unit;

    private Double purchasePrice;

    private Double price;

    private Boolean isDiscount;

    private String supplier;

    private LocalDateTime produceDate;

    private Integer expirationTime;

    private String parent;

    private String brother;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime isDeleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
