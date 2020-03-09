package com.lyk.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long parent_id;
    private String content;
    private int type;
}
