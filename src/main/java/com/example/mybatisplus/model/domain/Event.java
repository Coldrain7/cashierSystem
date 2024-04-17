package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author harry
 * @since 2024-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Event对象", description="")
public class Event extends Model<Event> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private LocalDate beginTime;

    private LocalDate endTime;

    private Double price;

    private Double eventPrice;

    private Long comId;

    private Boolean isDone;

    private Double amount;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
    @TableLogic
    private Boolean isDeleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
