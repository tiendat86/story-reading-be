package com.storyreadingbe.repository;

import com.storyreadingbe.entity.Book;
import com.storyreadingbe.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
