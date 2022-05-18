package com.storyreadingbe.repository;

import com.storyreadingbe.dto.common.BookLastUpdateDTO;
import com.storyreadingbe.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRespository extends JpaRepository<Chapter, Integer> {
    Integer countAllByIdBook(Integer id);
    List<Chapter> findAllByIdBook(Integer id);
    @Query(value = "SELECT MAX(numerical) " +
            "FROM storyreading.chapter " +
            "WHERE id_book = ?", nativeQuery = true)
    Integer getNumbericalByBook(Integer id);
    
    @Query(value = "SELECT new com.storyreadingbe.dto.common.BookLastUpdateDTO(c.idBook, MAX(c.time)) " +
            "FROM Chapter c " +
            "GROUP BY c.idBook " +
            "ORDER BY MAX(c.time) DESC")
    List<BookLastUpdateDTO> getLastBookUpdate();

    @Query(value = "SELECT MIN(id) FROM storyreading.chapter " +
            "WHERE id_book = ?", nativeQuery = true)
    Integer getMinChapterByBook(Integer idBook);
    
    @Query(value = "SELECT * FROM storyreading.chapter " +
            "WHERE id_book = ? " +
            "AND id > ? " +
            "ORDER BY id ASC " +
            "LIMIT 1", nativeQuery = true)
    Chapter getNextChapter(Integer idBook, Integer idChapter);

    @Query(value = "SELECT * FROM storyreading.chapter " +
            "WHERE id_book = ? " +
            "AND id < ? " +
            "ORDER BY id DESC " +
            "LIMIT 1", nativeQuery = true)
    Chapter getLastChapter(Integer idBook, Integer idChapter);
}
