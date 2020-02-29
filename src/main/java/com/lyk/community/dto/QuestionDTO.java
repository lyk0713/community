package com.lyk.community.dto;

import com.lyk.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
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

    private User user;
}
