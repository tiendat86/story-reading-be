package com.storyreadingbe.dto.response;

import lombok.Data;

@Data
public class UserResponseDTO {
    private String username;
    private String fullname;
    private String urlImg;
    private Integer idAuthor;
}
