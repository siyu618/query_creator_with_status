package com.study.query_creator.dao;

import com.study.query_creator.model.RequestCreateStatusBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RequestCreateStatusBeanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RequestCreateStatusBean record);

    int insertSelective(RequestCreateStatusBean record);

    RequestCreateStatusBean selectByPrimaryKey(Integer id);

    List<RequestCreateStatusBean> getRequestByTypeAndStatus(@Param("request_type") String requestType, @Param("request_status") Integer status);
    List<RequestCreateStatusBean> getRequestByStatus(@Param("request_status") Integer status);

    int updateByPrimaryKeySelective(RequestCreateStatusBean record);

    int updateByPrimaryKey(RequestCreateStatusBean record);
}