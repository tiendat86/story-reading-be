package com.storyreadingbe.service;

import com.storyreadingbe.dto.common.BookLastUpdateDTO;
import com.storyreadingbe.dto.request.SearchFilterDTO;
import com.storyreadingbe.dto.response.BookDetailResponseDTO;
import com.storyreadingbe.dto.response.BookResponseDTO;
import com.storyreadingbe.entity.*;
import com.storyreadingbe.repository.*;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
    private CategoryRepository categoryRepository;
    @Autowired
    private BookCategoryRepository bookCategoryRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ModelMapper modelMapper;

    public BookResponseDTO getBookById(Integer id) {
        try {
            Book book = bookRepository.findById(id).get();
            BookResponseDTO dto = modelMapper.map(book, BookResponseDTO.class);
            dto.setPseudonym(authorRepository.findById(book.getIdAuthor()).get().getPseudonym());
            dto.setNumChapter(chapterRespository.countAllByIdBook(id));
            List<BookCategory> cates = bookCategoryRepository.findAllByIdBook(id);
            List<String> categories = new ArrayList<>();
            cates.forEach(bookCategory -> {
               categories.add(categoryRepository.findById(bookCategory.getIdCategory()).get().getName());
            });
            dto.setCategories(categories);
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
            List<BookCategory> cates = bookCategoryRepository.findAllByIdBook(book.getId());
            List<String> categories = new ArrayList<>();
            cates.forEach(bookCategory -> {
                categories.add(categoryRepository.findById(bookCategory.getIdCategory()).get().getName());
            });
            dto.setCategories(categories);
            res.add(dto);
        });
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
                List<BookCategory> cates = bookCategoryRepository.findAllByIdBook(bookLastUpdateDTO.getIdBook());
                List<String> categories = new ArrayList<>();
                cates.forEach(bookCategory -> {
                    categories.add(categoryRepository.findById(bookCategory.getIdCategory()).get().getName());
                });
                dto.setCategories(categories);
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
                List<BookCategory> cates = bookCategoryRepository.findAllByIdBook(bookLastUpdateDTO.getIdBook());
                List<String> categories = new ArrayList<>();
                cates.forEach(bookCategory -> {
                    categories.add(categoryRepository.findById(bookCategory.getIdCategory()).get().getName());
                });
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
                List<BookCategory> cates = bookCategoryRepository.findAllByIdBook(bookLastUpdateDTO.getIdBook());
                List<String> categories = new ArrayList<>();
                cates.forEach(bookCategory -> {
                    categories.add(categoryRepository.findById(bookCategory.getIdCategory()).get().getName());
                });
                res.add(dto);
            });
            return res;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Can't create the book");
        }
    }

    public List<BookResponseDTO> filterBook(SearchFilterDTO searchFilterDTO) {
        List<Book> list;
        if (!searchFilterDTO.getName().equals("") && searchFilterDTO.getComplete() != null)
            list = bookRepository.findAllByNameContainsAndComplete(searchFilterDTO.getName(), searchFilterDTO.getComplete());
        else if (!searchFilterDTO.getName().equals("") && searchFilterDTO.getComplete() == null)
            list = bookRepository.findByNameContains(searchFilterDTO.getName());
        else if (searchFilterDTO.getName().equals("") && searchFilterDTO.getComplete() != null)
            list = bookRepository.findAllByComplete(searchFilterDTO.getComplete());
        else 
            list = bookRepository.findAll();
        if (searchFilterDTO.getCategories() != null && searchFilterDTO.getCategories().size() > 0) {
            list = list.stream().filter(book -> filterCategory(book, searchFilterDTO)).collect(Collectors.toList());
        }
        if (searchFilterDTO.getSortBy() != null && searchFilterDTO.getSortBy().equals("rating"))
            sortBookByRating(list);
        if (searchFilterDTO.getSortBy() != null && searchFilterDTO.getSortBy().equals("time"))
            sortBookByTime(list);
        List<BookResponseDTO> res = new ArrayList<>();
        list.forEach(book -> {
            BookResponseDTO dto = getBookById(book.getId());
            List<BookCategory> cates = bookCategoryRepository.findAllByIdBook(book.getId());
            List<String> categories = new ArrayList<>();
            cates.forEach(bookCategory -> {
                categories.add(categoryRepository.findById(bookCategory.getIdCategory()).get().getName());
            });
            dto.setCategories(categories);
            res.add(dto);
        });
        return res;
    }
    
    private Boolean filterCategory(Book book, SearchFilterDTO dto) {
        List<BookCategory> cates = bookCategoryRepository.findAllByIdBook(book.getId());
        List<String> categories = new ArrayList<>();
        cates.forEach(bookCategory -> {
            categories.add(categoryRepository.findById(bookCategory.getIdCategory()).get().getName());
        });
        for (String category : categories) {
            if (dto.getCategories().contains(category))
                return true;
        }
        return false;
    }
    
    private void sortBookByRating(List<Book> list) {
        Collections.sort(list, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                Float avg1, avg2;
                Float sum;
                List<Review> reviews = reviewRepository.findAllByIdBook(o1.getId());
                if (reviews.size() ==0) {
                    avg1 = -1f;
                } else {
                    sum = 0f;
                    for(Review review : reviews) {
                        sum += Float.parseFloat(review.getRating());
                    }
                    avg1 = sum / reviews.size();
                }
                reviews = reviewRepository.findAllByIdBook(o2.getId());
                if (reviews.size() ==0) {
                    avg2 = -1f;
                } else {
                    sum = 0f;
                    for(Review review : reviews) {
                        sum += Float.parseFloat(review.getRating());
                    }
                    avg2 = sum / reviews.size();
                }
                return (int) (avg2 - avg1);
            }
        });
    }
    
    private void sortBookByTime(List<Book> list) {
        Collections.sort(list, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Chapter chapter1 = chapterRespository.getNewChapterByBook(o1.getId());
                Chapter chapter2 = chapterRespository.getNewChapterByBook(o2.getId());
                try {
                    return sdf.parse(chapter2.getTime()).compareTo(sdf.parse(chapter1.getTime()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
