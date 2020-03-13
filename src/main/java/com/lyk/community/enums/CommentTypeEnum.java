package com.lyk.community.enums;

public enum CommentTypeEnum {
    Question(1), COMMENT(2);
    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
