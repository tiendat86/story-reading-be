package com.storyreadingbe.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SearchFilterDTO {
    private String name;
    private List<String> categories;
    private Boolean complete;
    private String sortBy;
}
