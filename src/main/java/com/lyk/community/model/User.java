package com.lyk.community.model;

public class User {

    private int user_id;
    private String account_id;
    private String user_name;
    private String user_token;
    private Long user_create_time;
    private Long user_update_time;

    public User(String account_id, String user_name, String user_token, Long user_create_time, Long user_update_time) {
        this.account_id = account_id;
        this.user_name = user_name;
        this.user_token = user_token;
        this.user_create_time = user_create_time;
        this.user_update_time = user_update_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public Long getUser_create_time() {
        return user_create_time;
    }

    public void setUser_create_time(Long user_create_time) {
        this.user_create_time = user_create_time;
    }

    public Long getUser_update_time() {
        return user_update_time;
    }

    public void setUser_update_time(Long user_update_time) {
        this.user_update_time = user_update_time;
    }
}
