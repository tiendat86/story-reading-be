package com.storyreadingbe.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chapter")
@Data
public class Chapter {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    @Column(name = "time")
    private String time;
    @Column(name = "numerical")
    private Integer numerical;
    @Column(name = "id_book")
    private Integer idBook;
}
