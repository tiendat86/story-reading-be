package com.storyreadingbe.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "book")
@Data
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "complete")
    private Boolean complete;
    @Column(name = "url_img")
    private String urlImg;
    @Lob
    @Column(name = "brief", columnDefinition = "TEXT")
    private String brief;
    @Column(name = "id_author")
    private Integer idAuthor;
}
