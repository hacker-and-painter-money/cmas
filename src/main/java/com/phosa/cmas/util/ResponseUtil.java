package com.phosa.cmas.util;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.phosa.cmas.constant.ErrorResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static ResponseEntity<?> getSuccessResponse(Object data) {
        return getSuccessResponse("OK", data);
    }
    public static ResponseEntity<?> getSuccessResponse(String msg, Object object) {
        return getResponse(0, msg, JsonUtil.parseStringToJsonElement(JsonUtil.toJson(object)));
    }


    public static ResponseEntity<?> getFailResponse(ErrorResponse error) {
        return getResponse(error.getCode(), error.getMsg(), null);
    }

    public static ResponseEntity<?> getResponse(int code, String msg, JsonElement data) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", code);
        jsonObject.addProperty("msg", msg);
        if (data != null) {
            jsonObject.add("data", data);
        }
        HttpHeaders headers = new HttpHeaders();
        //根据自己的需要动态添加你想要的content type
        headers.add(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<>(jsonObject.toString(), headers, HttpStatus.OK);
    }
}
