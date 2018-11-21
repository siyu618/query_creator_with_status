package com.study.query_creator.common.util.auditlog;

import lombok.Builder;
import org.springframework.boot.logging.LogLevel;

import java.util.Objects;

@Builder
public class AuditLogBean {
    public static final String PREFIX = "auditlog";
    private static final String FIELD_SEPERATOR = "=";
    private static final String ITEM_SEPERATOR = "||";
    private static final String LOG_LEVEL_INFO = LogLevel.INFO.toString();
    //private Long id;
    // auditlog||system=mis||hostIp=127.0.0.1||userName=***
    // ||url=http://action
    // ||getParams=||postParams={"xxx":"xxx"}||userIp=127.0.0.1
    // ||timestamp=2018-10-31 12:32:12||response=
    // ||traceid=xxx||level=3||result=200

    private String system;
    private String hostIp;
    private String userName;
    private String url;
    private String getParams;
    private String postParams;
    private String userIp;
    private String timestamp;
    private String response;
    private String traceId;
    @Builder.Default
    private String level = LOG_LEVEL_INFO;
    @Builder.Default
    private int result = 200;

    public String toAuditLog() {
        StringBuilder sb = new StringBuilder(PREFIX);
        append(sb, "system", system);
        append(sb, "hostIp", hostIp);
        append(sb, "userName", userName);
        append(sb, "url", url);
        append(sb, "getParams", getParams);
        append(sb, "postParams", postParams);
        append(sb, "userIp", userIp);
        append(sb, "timestamp", timestamp);
        append(sb, "response", response);
        append(sb, "traceid", traceId);
        append(sb, "level", level);
        append(sb, "result", String.valueOf(result));
        return sb.toString();
    }

    private static void append(StringBuilder sb, String name, String value) {
        if (Objects.isNull(sb)) {
            return;
        }
        sb.append(ITEM_SEPERATOR)
                .append(name)
                .append(FIELD_SEPERATOR)
                .append(value);
    }
}

