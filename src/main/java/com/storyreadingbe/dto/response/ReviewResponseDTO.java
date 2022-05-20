package com.storyreadingbe.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDTO {
    private Integer id;
    private String content;
    private String rating;
    private String username;
    private String fullname;
    private String urlImg;
    private Integer idBook;
    protected LocalDateTime createdAt;
}
