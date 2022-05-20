package com.storyreadingbe.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "book_category")
@Data
public class BookCategory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "id_book")
    private Integer idBook;
    @Column(name = "id_category")
    private Integer idCategory;
}
