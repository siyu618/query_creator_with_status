package com.study.query_creator.request;

import java.util.HashMap;
import java.util.Map;

/**
 * @author didi
 */

public enum RequestCreateStatusEnum {
    /**
     *
     */
    CREATE_CANCELED(2, "create_canceled")
    /**
     *
     */
    ,
    CREATE_FAILED(3, "create_failed"),
    /**
     *
     */
    CREATE_RETRYING(1, "create_retrying")
    /**
     *
     */
    ,
    CREATE_SUCCESSFULLY(0, "create_successfully"),
    /**
     *
     */
    UNKNOWN(-1, "unknown");


    private String status;
    private int id;

    RequestCreateStatusEnum(int id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, RequestCreateStatusEnum> ID2EnumMap = new HashMap<>();

    static {
        for (RequestCreateStatusEnum status : RequestCreateStatusEnum.values()) {
            ID2EnumMap.put(status.id, status);
        }
    }

    public static RequestCreateStatusEnum getStatus(Integer id) {
        if (null == id || !ID2EnumMap.containsKey(id)) {
            return UNKNOWN;
        }
        return ID2EnumMap.get(id);
    }

    public int getIntValue() {
        return id;
    }

}
