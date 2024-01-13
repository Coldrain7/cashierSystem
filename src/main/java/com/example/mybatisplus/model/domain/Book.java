package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @author lxp
 * @since 2023-06-17
 */
@Data//自动补全get,set方法
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)//开启链式写法
@ApiModel(value="Book对象", description="")
public class Book extends Model<Book> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private Double price;

    private String author;

    @TableField("creatorId")
    private Integer creatorId;

    private String remark;
    @TableField(exist = false)
    private Admin admin;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
