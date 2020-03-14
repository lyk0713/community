package com.lyk.community.dto;

import com.lyk.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private Long create_time;
    private Integer outerId;
    private Integer status;
    private String notifier_name;
    private String outer_title;
    private String typeName;
    private Integer type;
}
