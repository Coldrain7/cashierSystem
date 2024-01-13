package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.dto.PageDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.BookService;
import com.example.mybatisplus.model.domain.Book;

import java.util.ArrayList;
import java.util.List;


/**
 *
 *  前端控制器
 *
 *
 * @author lxp
 * @since 2023-06-17
 * @version v1.0
 */
@Controller
@RequestMapping("/api/book")
public class BookController {

    private final Logger logger = LoggerFactory.getLogger( BookController.class );

    @Autowired
    private BookService bookService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Book  book =  bookService.getById(id);
        return JsonResponse.success(book);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        bookService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateBook(Book  book) throws Exception {
        bookService.updateById(book);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Book
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Book  book) throws Exception {
        bookService.save(book);
        return JsonResponse.success(null);
    }
    @ResponseBody
    @GetMapping(value = "/allBooks")
    public JsonResponse getAllBooks()
    {
        List<Book> books = new ArrayList<>();
        books = bookService.getAllBooks();
        return JsonResponse.success(books);
    }
    @ResponseBody
    @GetMapping(value =  "/pageList")
    public JsonResponse pageList(PageDTO pageDTO, Book book)
    {
        Page<Book> page = bookService.pageList(pageDTO, book);
        return JsonResponse.success(page);
    }
}

