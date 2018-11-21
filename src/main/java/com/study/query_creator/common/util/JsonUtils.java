package com.study.query_creator.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public final class JsonUtils {
    private static ObjectMapper objectMapper;

    static {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(df);

        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * From json string to bean.
     *
     * @param <T>   bean class type.
     * @param json  json string.
     * @param clazz bean class.
     * @return the bean instance.
     */
    public static <T> T toBean(String json, Class<T> clazz) {
        if (json != null && json.length() > 0) {
            try {
                return objectMapper.readValue(json, clazz);
            } catch (Exception e) {
                throw new RuntimeException("JSONString : " + json, e);
            }
        }
        return null;
    }

    /**
     * From json string to bean list.
     *
     * @param <T>   <T>
     * @param json  json
     * @param clazz clazz
     * @return ArrayList<T>
     */
    public static <T> LinkedList<T> toBeanList(String json, Class<T> clazz) {
        if (json != null && json.length() > 0) {
            try {
                return objectMapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(LinkedList.class, clazz));
            } catch (Exception e) {
                throw new RuntimeException("JSONString : " + json, e);
            }
        }
        return null;
    }

    public static <T> LinkedList<T> toBeanLinkedList(String json, Class<T> clazz) {
        if (json != null && json.length() > 0) {
            try {
                return objectMapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(LinkedList.class, clazz));
            } catch (Exception e) {
                throw new RuntimeException("JSONString : " + json, e);
            }
        }
        return null;
    }



    /**
     * To json string base on given mapper.
     *
     * @param object object
     * @return json string.
     */
    public static String toString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转化成 JsonNode
     */
    public static JsonNode toJsonNode(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

