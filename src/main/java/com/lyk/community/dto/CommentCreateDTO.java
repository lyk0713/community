package com.lyk.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private Integer parent_id;
    private String content;
    private Integer type;
}
