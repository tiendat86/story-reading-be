package com.storyreadingbe.controller;

import com.storyreadingbe.common.URLConst;
import com.storyreadingbe.dto.common.BookLastUpdateDTO;
import com.storyreadingbe.dto.response.BookDetailResponseDTO;
import com.storyreadingbe.dto.response.BookResponseDTO;
import com.storyreadingbe.entity.Book;
import com.storyreadingbe.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookServiceImpl bookService;

    @Value("${book.limit}")
    Integer limit;

    @GetMapping(value = URLConst.User.ALL_BOOK)
    public List<Book> getAllBook() {
        return bookService.getAllBook();
    }

    @GetMapping(value = URLConst.User.GET_BOOK + "/{id}")
    public BookResponseDTO getBookById(@PathVariable Integer id) {
        return bookService.getBookById(id);
    }

    @GetMapping(value = URLConst.User.GET_DETAIL_BOOK + "/{id}")
    public BookDetailResponseDTO getDetailBookById(@PathVariable Integer id) {
        return bookService.getDetailBookById(id);
    }

    @GetMapping(value = URLConst.User.FIND_BOOK + "/{key}")
    public List<BookResponseDTO> searchBookByKey(@PathVariable String key) {
        return bookService.searchBookByKey(key);
    }

    @PostMapping(value = URLConst.Author.CREATE_BOOK)
    public void createBook(@ModelAttribute Book book) {
        bookService.createBook(book);
    }
    
    @GetMapping(value = URLConst.User.GET_BOOK_LAST_UPDATE)
    public List<BookResponseDTO> sortBookByDate() {
        return bookService.sortBookByDate(limit);
    }

    @GetMapping(value = URLConst.User.GET_BOOK_LAST_UPDATE_COMPLETE)
    public List<BookResponseDTO> sortBookByDateComplete() {
        return bookService.sortBookByDateComplete(limit);
    }

    @GetMapping(value = URLConst.User.GET_BOOK_LAST_UPDATE_NOT_COMPLETE)
    public List<BookResponseDTO> sortBookByDateNotComplete() {
        return bookService.sortBookByDateNotComplete(limit);
    }
}
