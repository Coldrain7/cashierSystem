package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lxp
 * @since 2023-06-17
 */
@Repository
public interface BookMapper extends BaseMapper<Book> {

    Page<Book> pageList(@Param("page") Page<Book> page, @Param("book") Book book);

    Book selectByIdWithAdmin(@Param("id") Long i);
}
