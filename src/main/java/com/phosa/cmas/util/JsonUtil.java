package com.phosa.cmas.util;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * @author phosa.gao
 */
public class JsonUtil {
    JsonUtil(){}
    static Gson gson = new Gson();
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
    public static String toJsonWithExpose(Object obj) {
        return gson.newBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(obj);
    }

    public static <T> T parseJsonToObject(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
    public static <T> List<T> parseJsonToList(String json, Class<T> clazz) {
        return gson.fromJson(json, new TypeToken<List<T>>(){}.getType());
    }
    public static <T> T parseObjectToObject(Object obj, Class<T> clazz) {
        return parseJsonToObject(gson.toJson(obj), clazz);
    }
    public static <T> List<T> parseListToList(List<?> oriList, Class<T> clazz) {
        return parseJsonToList(gson.toJson(oriList), clazz);
    }
    public static JsonElement parseStringToJsonElement(String json) {
        return JsonParser.parseString(json);
    }
}

