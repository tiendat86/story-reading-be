package com.storyreadingbe.repository;

import com.storyreadingbe.entity.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Integer> {
    List<BookCategory> findAllByIdBook(Integer idBook);
}
