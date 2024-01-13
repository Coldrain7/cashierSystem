package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Book;
import com.example.mybatisplus.mapper.BookMapper;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lxp
 * @since 2023-06-17
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private BookMapper bookMapper;
    @Override
    public List<Book> getAllBooks() {
        return bookMapper.selectList(null);
    }

    @Override
    public Page<Book> pageList(PageDTO pageDTO, Book book) {
        Page<Book> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        //判断字符串不为null且不为''
        if(StringUtils.isNotBlank(book.getName()))
        {
            wrapper.like("name", book.getName());
        }
        if(StringUtils.isNotBlank(book.getAuthor()))
        {
            wrapper.like("author", book.getAuthor());
        }
        bookMapper.selectPage(page, wrapper);
        return page;
    }
}
