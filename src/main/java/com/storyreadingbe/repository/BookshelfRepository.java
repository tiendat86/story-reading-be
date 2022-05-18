package com.storyreadingbe.repository;

import com.storyreadingbe.entity.Bookshelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookshelfRepository extends JpaRepository<Bookshelf, Integer> {
    List<Bookshelf> findTop20ByIdUserOrderByUpdatedAtDesc(String username);
    List<Bookshelf> findAllByIdUserAndFavorite(String username, Boolean favorite);
    Integer countAllByIdUserAndIdBook(String username, Integer idBook);
    Bookshelf findByIdUserAndIdBook(String username, Integer idBook);
}
