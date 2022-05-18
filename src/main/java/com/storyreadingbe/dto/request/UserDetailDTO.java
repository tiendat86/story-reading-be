package com.storyreadingbe.dto.request;

import lombok.Data;

@Data
public class UserDetailDTO {
    private String username;
    private String fullname;
    private String urlImg;
    private Integer idAuthor;
}
