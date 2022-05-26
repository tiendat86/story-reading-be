package com.storyreadingbe.repository;

import com.storyreadingbe.dto.response.BookResponseDTO;
import com.storyreadingbe.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "SELECT book.* FROM book, author " +
            "WHERE book.id_author = author.id " +
            "AND (book.name LIKE ? " +
            "OR author.pseudonym LIKE ?)", nativeQuery = true)
    List<Book> getBookByKey(String key1, String key2);
    
    @Query(value = "SELECT * FROM storyreading.book " +
            "ORDER BY RAND() " +
            "LIMIT ?", nativeQuery = true)
    List<Book> getRandomLimitBook(Integer limit);
    
    List<Book> findAllByNameContainsAndComplete(String name, Boolean complete);
    List<Book> findByNameContains(String name);

    List<Book> findAllByComplete(Boolean complete);
}
