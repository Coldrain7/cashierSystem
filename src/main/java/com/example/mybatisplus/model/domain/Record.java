package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * 销售记录
 * </p>
 *
 * @author harry
 * @since 2024-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Record对象", description="销售记录")
public class Record extends Model<Record> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    //数据库中id与com_id一起共同作为主键
    private Long id;

    private Integer memId;

    private Long comId;

    private Integer workerId;
    //数量
    private Double number;
    //总价钱
    private Double payment;
    //支付方式
    private Integer method;
    //类型，比如是否是挂单
    private Integer type;
    @TableLogic
    private Boolean isDeleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
