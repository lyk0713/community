package com.lyk.community.model;

import lombok.Data;

@Data
public class Question {
    private int id;
    private String title;
    private String description;
    private int creator;
    private int comment_count;
    private int view_count;
    private int link_count;
    private String tag;
    private Long create_time;
    private Long update_time;
}
