package com.storyreadingbe.service;

import com.storyreadingbe.dto.response.BookResponseDTO;
import com.storyreadingbe.dto.response.BookshelfResponseDTO;
import com.storyreadingbe.entity.Book;
import com.storyreadingbe.entity.Bookshelf;
import com.storyreadingbe.repository.AuthorRepository;
import com.storyreadingbe.repository.BookRepository;
import com.storyreadingbe.repository.ChapterRespository;
import com.storyreadingbe.repository.BookshelfRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookshelfServiceImpl {
    @Autowired
    private BookshelfRepository bookshelfRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ChapterRespository chapterRespository;
    @Autowired
    ModelMapper modelMapper;

    public List<BookResponseDTO> getHistoryBookByUser(String username) {
        try {
            List<BookResponseDTO> res = new ArrayList<>();
            List<Bookshelf> bookshelves = bookshelfRepository.findTop20ByIdUserOrderByUpdatedAtDesc(username);
            bookshelves.forEach(bookshelf -> {
                Book book = bookRepository.findById(bookshelf.getIdBook()).get();
                BookResponseDTO dto = modelMapper.map(book, BookResponseDTO.class);
                dto.setPseudonym(authorRepository.findById(book.getIdAuthor()).get().getPseudonym());
                dto.setNumChapter(chapterRespository.countAllByIdBook(book.getId()));
                dto.setIdChapterLastRead(bookshelf.getReading());
                res.add(dto);
            });
            return res;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Can't get bookshelf by " + username);
        }
    }

    public List<BookResponseDTO> getFavoriteBookByUser(String username) {
        try {
            List<BookResponseDTO> res = new ArrayList<>();
            List<Bookshelf> bookshelves = bookshelfRepository.findAllByIdUserAndFavorite(username, true);
            bookshelves.forEach(bookshelf -> {
                Book book = bookRepository.findById(bookshelf.getIdBook()).get();
                BookResponseDTO dto = modelMapper.map(book, BookResponseDTO.class);
                dto.setPseudonym(authorRepository.findById(book.getIdAuthor()).get().getPseudonym());
                dto.setNumChapter(chapterRespository.countAllByIdBook(book.getId()));
                dto.setIdChapterLastRead(bookshelf.getReading());
                res.add(dto);
            });
            return res;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Can't get bookshelf by " + username);
        }
    }

    public void addBookToBookshelf(Bookshelf bookshelf) {
        try {
            Bookshelf item = bookshelfRepository.findByIdUserAndIdBook(bookshelf.getIdUser(), bookshelf.getIdBook());
            if (item == null) {
                bookshelf.setReading(chapterRespository.getMinChapterByBook(bookshelf.getIdBook()));
                bookshelfRepository.save(bookshelf);
            } else {
                item.setFavorite(true);
                bookshelfRepository.save(item);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Can't save book");
        } 
    }
    
    public BookshelfResponseDTO getBookshelfByBookAndUser(String username, Integer idBook) {
        try {
            Bookshelf bookshelf = bookshelfRepository.findByIdUserAndIdBook(username, idBook);
            BookshelfResponseDTO dto = modelMapper.map(bookshelf, BookshelfResponseDTO.class);
            return dto;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Something went wrong");
        }
    }

    public void removeBookshelf(Integer id) {
        try {
            Bookshelf bookshelf = bookshelfRepository.findById(id).get();
            bookshelf.setFavorite(false);
            bookshelfRepository.save(bookshelf);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Something went wrong");
        }
    }

    public void saveStatusUserRead(String username, Integer idBook, Integer idChapter) {
        try {
            Bookshelf item = bookshelfRepository.findByIdUserAndIdBook(username, idBook);
            if (item == null) {
                Bookshelf bookshelf = new Bookshelf();
                bookshelf.setReading(idChapter);
                bookshelf.setFavorite(false);
                bookshelf.setIdBook(idBook);
                bookshelf.setIdUser(username);
                bookshelfRepository.save(bookshelf);
            } else {
                item.setReading(idChapter);
                bookshelfRepository.save(item);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Something went wrong");
        }
    }
}
