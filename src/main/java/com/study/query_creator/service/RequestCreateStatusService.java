package com.study.query_creator.service;

import com.study.query_creator.model.RequestCreateStatusBean;

import java.util.List;

public interface RequestCreateStatusService {
    public List<RequestCreateStatusBean> getRequestByTypeAndStatus(String requestType, Integer status);
    public List<RequestCreateStatusBean> getRequestByStatus(Integer status);
    public int update(RequestCreateStatusBean bean);
    public int insert(RequestCreateStatusBean bean);

}

