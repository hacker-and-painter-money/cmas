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
    USERNAME_NOT_EXIST (103, "用户名已存在");



    final int code;
    final String msg;
    ErrorResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
