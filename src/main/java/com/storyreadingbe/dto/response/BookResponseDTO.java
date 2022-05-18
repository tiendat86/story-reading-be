package com.storyreadingbe.dto.response;

import lombok.Data;

@Data
public class BookResponseDTO {
    private Integer id;
    private String name;
    private Boolean complete;
    private String urlImg;
    private String pseudonym;
    private Integer numChapter;
    private Integer idChapterLastRead;
}
