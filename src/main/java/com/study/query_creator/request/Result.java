package com.study.query_creator.request;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author 
 */
@Data
@Builder
public class Result {
    private String status;
    private Object result;
}
