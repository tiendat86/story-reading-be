package com.storyreadingbe.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Data
public class Review {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    @Column(name = "rating")
    private String rating;
    @Column(name = "username")
    private String username;
    @Column(name = "id_book")
    private Integer idBook;
    @CreationTimestamp
    @Column(name = "created_at")
    protected LocalDateTime createdAt;
}
