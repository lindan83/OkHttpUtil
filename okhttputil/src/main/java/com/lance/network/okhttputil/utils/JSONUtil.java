package com.lance.network.okhttputil.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * JSON工具
 */
public class JSONUtil {
    private JSONUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static Gson gson = new Gson();

    /**
     * 将JSON文本将化为指定类型对象
     *
     * @param json    json String
     * @param toClass 转换的类型Class
     * @param <T>     类型参数
     * @return 目标类型对象
     */
    public static <T> T getObjectFromJson(String json, Class<T> toClass) {
        if (TextUtils.isEmpty(json) || toClass == null) {
            return null;
        }
        try {
            return gson.fromJson(json, toClass);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将JSON文本将化为指定类型对象
     *
     * @param json   json String
     * @param toType 转换的类型Type
     * @param <T>    类型参数
     * @return 目标类型对象
     */
    public static <T> T getObjectFromJson(String json, Type toType) {
        if (TextUtils.isEmpty(json) || toType == null) {
            return null;
        }
        try {
            return gson.fromJson(json, toType);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将任意对象转换为JSON文本
     *
     * @param object 任意对象
     * @return json String
     */
    public static String getJsonFromObject(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return gson.toJson(object);
        } catch (Exception e) {
            return null;
        }
    }
}
