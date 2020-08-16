package com.resqlity.orm.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.resqlity.orm.models.responses.ResqlityResponse;

import java.lang.reflect.Type;

public class JsonHelper {

    /**
     * @param jsonString Json String
     * @param tClass     Class To Deserialize
     * @param <T>        Class Type
     * @return Object of specified type
     */
    public static <T> T Deserialize(String jsonString, Class<T> tClass) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, tClass);
    }

    /**
     * @param obj Object to serialize
     * @param <T> Object Type
     * @return Json String
     */
    public static <T> String Serialize(T obj) {
        Gson gson = new Gson();
        Type fooType = new TypeToken<T>() {
        }.getType();
        return gson.toJson(obj, fooType);
    }

}
