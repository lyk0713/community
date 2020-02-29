package com.lyk.community.model;

import lombok.Data;

@Data
public class User {

    private int id;
    private String account_id;
    private String name;
    private String token;
    private Long create_time;
    private Long update_time;
    private String avatar_url;
}
