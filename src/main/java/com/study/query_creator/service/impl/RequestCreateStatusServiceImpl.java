package com.study.query_creator.service.impl;

import com.study.query_creator.dao.RequestCreateStatusBeanMapper;
import com.study.query_creator.model.RequestCreateStatusBean;
import com.study.query_creator.service.RequestCreateStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestCreateStatusServiceImpl implements RequestCreateStatusService {

    @Autowired
    private RequestCreateStatusBeanMapper mapper;


    @Override
    public List<RequestCreateStatusBean> getRequestByTypeAndStatus(String requestType, Integer status) {
        return mapper.getRequestByTypeAndStatus(requestType, status);
    }

    @Override
    public List<RequestCreateStatusBean> getRequestByStatus(Integer status) {
        return mapper.getRequestByStatus(status);
    }

    @Override
    public int update(RequestCreateStatusBean bean) {
        return mapper.updateByPrimaryKey(bean);
    }

    @Override
    public int insert(RequestCreateStatusBean bean) {
        return mapper.insert(bean);
    }
}
