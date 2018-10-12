package com.study.query_creator.controller;


import com.study.query_creator.model.RequestCreateStatusBean;
import com.study.query_creator.request.RequestCreateStatusEnum;
import com.study.query_creator.request.Result;
import com.study.query_creator.service.RequestCreateStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class RequestController {
    @Autowired
    RequestCreateStatusService requestCreateStatusService;

    @RequestMapping(value = "/requestsByStatus/{status}", method = RequestMethod.GET)
    public ResponseEntity<Result> getUserList(@PathVariable("status") String status) {
        Result result = Result.builder().build();
        try {
            List<RequestCreateStatusBean> requestCreateStatusBeans = requestCreateStatusService.getRequestByStatus(Integer.parseInt(status));
            result.setResult(requestCreateStatusBeans);
            result.setStatus("OK");
        } catch (Exception e ) {
            result.setStatus("failed");
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/requestsByTypeAndStatus/{request_type}/{status}", method = RequestMethod.GET)
    public ResponseEntity<Result> getUserListByTypeAndStatus(@PathVariable("request_type")String requestType, @PathVariable("status") String status) {
        Result result = Result.builder().build();
        try {
            List<RequestCreateStatusBean> requestCreateStatusBeans = requestCreateStatusService.getRequestByTypeAndStatus(requestType, Integer.parseInt(status));
            result.setResult(requestCreateStatusBeans);
            result.setStatus("OK");
        } catch (Exception e ) {
            result.setStatus("failed");
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/add/{requestId}/{requestType}", method = RequestMethod.GET)
    public ResponseEntity<Result> add(@PathVariable("requestId") String reqId, @PathVariable("requestType")String requestType) {
        Result result = Result.builder().build();
        try {
            RequestCreateStatusBean bean = new RequestCreateStatusBean();
            bean.setRequestId(reqId);
            bean.setDetail(reqId);
            bean.setCreateTime(new Date());
            bean.setLastUpdateTime(new Date());
            bean.setRetryTimes(0);
            bean.setRequestStatus(RequestCreateStatusEnum.UNKNOWN.getIntValue());
            bean.setRequestUrl("http://dummy.com/request");
            bean.setRequestPostData(null);
            bean.setRequestType(requestType);
            if (requestCreateStatusService.insert(bean) < 0)  {
                result.setStatus("failed");
            }
            else {
                result.setStatus("succeed");
            }
        } catch (Exception e ) {
            result.setStatus("failed");
        }
        return ResponseEntity.ok(result);

    }

}
