package com.study.query_creator.request;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Result {
    private String status;
    private Object result;
}
