package com.storyreadingbe.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "author")
@Data
public class Author {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "pseudonym")
    private String pseudonym;
    @Column(name = "age")
    private Integer age;
    @Column(name = "describe")
    private String describe;
}
