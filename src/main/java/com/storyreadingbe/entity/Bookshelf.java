package com.storyreadingbe.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookshelf")
@Data
public class Bookshelf {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "reading")
    private Integer reading;
    @Column(name = "favorite")
    private Boolean favorite;
    @Column(name = "id_user")
    private String idUser;
    @Column(name = "id_book")
    private Integer idBook;
    @CreationTimestamp
    @Column(name = "created_at")
    protected LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;
}
