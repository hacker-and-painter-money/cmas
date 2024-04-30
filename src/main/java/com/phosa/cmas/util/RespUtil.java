package com.phosa.cmas.util;


public class RespUtil {
    public static String getSuccessResp(String msg, Object object) {
        return getSuccessResp(msg, JsonUtil.toJson(object));
    }
    public static String getSuccessResp(String msg, String data) {
        return "{\"msg\":\"" + msg + "\",\"code\": \"10000\", \"data\": " + data + "}";
    }
    public static String getFailResp(String msg) {
        return "{\"msg\":\"" + msg + "\",\"code\": \"40000\"}";
    }
    public static String getDefaultSuccessResp(String data) {
        return getSuccessResp("OK", data);
    }
    public static String getDefaultFailResp() {
        return getFailResp("SERVER ERROR");
    }
}
