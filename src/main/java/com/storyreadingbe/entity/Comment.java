package com.storyreadingbe.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Data
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "rating")
    private Integer rating;
    @Column(name = "comment")
    private String comment;
    @Column(name = "time")
    private String time;
    @Column(name = "id_user")
    private String idUser;
    @Column(name = "id_book")
    private Integer idBook;
}
