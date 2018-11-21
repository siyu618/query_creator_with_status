package com.study.query_creator.model;

import java.io.Serializable;

public class JsonResult<T> implements Serializable {
    public static final int API_CODE_FAILURE = 1;
    public static final int API_CODE_SUCCESS = 0;

    public static final String SUCCESS_DESC = "success";

    private static final long serialVersionUID = -6268217109787098958L;

    private int errno;

    private T data;

    private String errmsg;

    public JsonResult(int errno, String errmsg, T data) {
        this.errno = errno;
        this.data = data;
        this.errmsg= errmsg;
    }

    public static <U> JsonResult<U> buildSuccessResult(U data) {
        JsonResult result = new JsonResult(API_CODE_SUCCESS, SUCCESS_DESC, data);
        return result;
    }

    public static <U> JsonResult<U> buildFailResult(int errno, String errmsg, U data) {
        JsonResult<U> result = new JsonResult(errno, errmsg, data);
        return result;
    }


    public static <U> JsonResult<U> of() {
        JsonResult result = new JsonResult(API_CODE_SUCCESS, "", null);
        return result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public int getErrno() {
        return errno;
    }

    public JsonResult<T> setErrno(int errno) {
        this.errno = errno;
        return this;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public JsonResult<T> setErrmsg(String errmsg) {
        this.errmsg = errmsg;
        return this;
    }
}
