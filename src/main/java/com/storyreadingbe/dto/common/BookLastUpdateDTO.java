package com.storyreadingbe.dto.common;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookLastUpdateDTO {
    private Integer idBook;
    private String lastTime;
}