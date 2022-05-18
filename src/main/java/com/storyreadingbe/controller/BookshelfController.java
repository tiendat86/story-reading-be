package com.storyreadingbe.controller;

import com.storyreadingbe.common.URLConst;
import com.storyreadingbe.dto.response.BookResponseDTO;
import com.storyreadingbe.dto.response.BookshelfResponseDTO;
import com.storyreadingbe.entity.Bookshelf;
import com.storyreadingbe.service.BookshelfServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookshelfController {
    @Autowired
    BookshelfServiceImpl bookshelfService;

    @PostMapping(value = URLConst.User.ADD_BOOKSHELF)
    public void addBookToBookshelf(@ModelAttribute Bookshelf bookshelf) {
        bookshelfService.addBookToBookshelf(bookshelf);
    }

    @PostMapping(value = URLConst.Author.GET_HISTORY_BOOK)
    public List<BookResponseDTO> getHistoryBookByUser(@RequestParam("username") String username) {
        return bookshelfService.getHistoryBookByUser(username);
    }

    @PostMapping(value = URLConst.Author.GET_FAVORITE_BOOK)
    public List<BookResponseDTO> getFavoriteBookByUser(@RequestParam("username") String username) {
        return bookshelfService.getFavoriteBookByUser(username);
    }
    
    @PostMapping(value = URLConst.Author.GET_BOOKSHELF_DETAIL)
    public BookshelfResponseDTO getBookshelfByUser(@RequestParam("username") String username, @RequestParam("idBook") Integer idBook) {
        return bookshelfService.getBookshelfByBookAndUser(username, idBook);
    }

    @GetMapping(value = URLConst.Author.REMOVE_BOOKSHELF + "/{id}")
    public void removeBookshelf(@PathVariable Integer id) {
        bookshelfService.removeBookshelf(id);
    }

    @PostMapping(value = URLConst.Author.UPDATE_STATUS_READ)
    public void saveStatusUserRead(@RequestParam("username") String username, @RequestParam("idBook") Integer idBook,
                                   @RequestParam("idChapter") Integer idChapter) {
        bookshelfService.saveStatusUserRead(username, idBook, idChapter);
    }
}
