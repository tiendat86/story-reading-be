package com.storyreadingbe.service;

import com.storyreadingbe.dto.common.BookLastUpdateDTO;
import com.storyreadingbe.dto.response.BookDetailResponseDTO;
import com.storyreadingbe.dto.response.BookResponseDTO;
import com.storyreadingbe.entity.Book;
import com.storyreadingbe.entity.Bookshelf;
import com.storyreadingbe.entity.Chapter;
import com.storyreadingbe.repository.AuthorRepository;
import com.storyreadingbe.repository.BookRepository;
import com.storyreadingbe.repository.BookshelfRepository;
import com.storyreadingbe.repository.ChapterRespository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ChapterRespository chapterRespository;
    @Autowired
    private BookshelfRepository bookshelfRepository;
    @Autowired
    private ModelMapper modelMapper;

    public BookResponseDTO getBookById(Integer id) {
        try {
            Book book = bookRepository.findById(id).get();
            BookResponseDTO dto = modelMapper.map(book, BookResponseDTO.class);
            dto.setPseudonym(authorRepository.findById(book.getIdAuthor()).get().getPseudonym());
            dto.setNumChapter(chapterRespository.countAllByIdBook(id));
            return dto;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Can't get book by id = " + id);
        }
    }

    public List<Book> getAllBook() {
        try {
            return bookRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Can't get all book");
        }
    }

    public void createBook(Book book) {
        try {
            bookRepository.save(book);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Can't create the book");
        }
    }

    public List<BookResponseDTO> searchBookByKey(String key) {
        List<BookResponseDTO> res = new ArrayList<>();
        String param = "%" + key + "%";
        List<Book> books = bookRepository.getBookByKey(param, param);
        books.forEach(book -> {
            BookResponseDTO dto = getBookById(book.getId());
            res.add(dto);
        });
        System.out.println(66666);
        return res;
    }

    public BookDetailResponseDTO getDetailBookById(Integer id) {
        try {
            return new BookDetailResponseDTO(bookRepository.findById(id).get().getBrief());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Can't create the book");
        }
    }

    public List<BookResponseDTO> sortBookByDate(Integer limit) {
        try {
            List<BookResponseDTO> res = new ArrayList<>();
            List<BookLastUpdateDTO> list = chapterRespository.getLastBookUpdate().stream().limit(limit).collect(Collectors.toList());
            list.forEach(bookLastUpdateDTO -> {
                BookResponseDTO dto = getBookById(bookLastUpdateDTO.getIdBook());
                res.add(dto);
            });
            return res;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Can't create the book");
        }
    }

    public List<BookResponseDTO> sortBookByDateComplete(Integer limit) {
        try {
            List<BookResponseDTO> res = new ArrayList<>();
            List<BookLastUpdateDTO> list = chapterRespository.getLastBookUpdate()
                    .stream()
                    .filter(bookLastUpdateDTO -> getBookById(bookLastUpdateDTO.getIdBook()).getComplete())
                    .limit(limit)
                    .collect(Collectors.toList());
            list.forEach(bookLastUpdateDTO -> {
                BookResponseDTO dto = getBookById(bookLastUpdateDTO.getIdBook());
                res.add(dto);
            });
            return res;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Can't create the book");
        }
    }

    public List<BookResponseDTO> sortBookByDateNotComplete(Integer limit) {
        try {
            List<BookResponseDTO> res = new ArrayList<>();
            List<BookLastUpdateDTO> list = chapterRespository.getLastBookUpdate()
                    .stream()
                    .filter(bookLastUpdateDTO -> (getBookById(bookLastUpdateDTO.getIdBook()).getComplete() == false))
                    .limit(limit)
                    .collect(Collectors.toList());
            list.forEach(bookLastUpdateDTO -> {
                BookResponseDTO dto = getBookById(bookLastUpdateDTO.getIdBook());
                res.add(dto);
            });
            return res;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Can't create the book");
        }
    }
}