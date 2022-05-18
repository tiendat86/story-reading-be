package com.storyreadingbe.dto.response;

import lombok.Data;

@Data
public class BookshelfResponseDTO {
    private Integer id;
    private Integer reading;
    private Boolean favorite;
    private String idUser;
    private Integer idBook;
}
