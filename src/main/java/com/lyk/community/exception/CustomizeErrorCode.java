package com.lyk.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(400,"问题找不到！"),
    TARGET_PARAM_NOT_FOUND(401, "问题已被删除！"),
    NO_LOGIN(300, "请先登录再评论"),
    SYS_ERROR(500,"服务器冒烟了！"),
    COMMENT_NOT_FOUND(402,"评论不存在！"),
    COMENT_EMPTY(301,"评论不能为空！");

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
