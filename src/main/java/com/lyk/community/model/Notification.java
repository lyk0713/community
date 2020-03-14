package com.lyk.community.model;

import lombok.Data;

@Data
public class Notification {
    private Integer id;
    private Integer notifier;
    private Integer receiver;
    private Integer outerId;
    private Integer type;
    private Integer status;
    private Long create_time;
    private String notifier_name;
    private String outer_title;
}
