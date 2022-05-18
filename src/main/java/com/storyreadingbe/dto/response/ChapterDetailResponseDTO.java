package com.storyreadingbe.dto.response;

import lombok.Data;

@Data
public class ChapterDetailResponseDTO {
    private Integer id;
    private String title;
    private String content;
    private String time;
    private Integer numerical;
    private Integer idBook;
    private Integer numChapter;
}
