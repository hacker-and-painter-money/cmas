package com.phosa.cmas.constant;

import lombok.Getter;

@Getter
public enum ErrorResponse {
    //公共错误
    SERVER_ERROR (1, "服务异常"),
    INVALID_ID (2, "错误ID"),
    ALREADY_DELETED (3, "该资源已删除"),

    //User
    WRONG_PASSWORD (101, "密码错误"),
    USERNAME_EXIST (102, "用户名已存在"),
    USERNAME_NOT_EXIST (103, "用户名不存在"),

    //ChatGroup
    USER_ID_NOT_EXIST (201, "用户ID不存在"),
    CHAT_GROUP_ID_NOT_EXIST (202, "聊天组ID不存在"),
    USER_ALREADY_JOINED (203, "用户已加入"),
    NAME_EXIST(204, "名称已存在"),

    //Point
    USER_ID_EXIST (301, "用户ID已存在"),


    Error(999, "错了");

    final int code;
    final String msg;
    ErrorResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
