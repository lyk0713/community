package com.lyk.community.model;

import lombok.Data;

@Data
public class Comment {

    private Integer id;
    private Integer parent_id;
    private Integer type;
    private Integer commentator;
    private Long link_count;
    private String content;
    private Long create_time;
    private Long update_time;
}
