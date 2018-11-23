package com.study.query_creator.web.controller;


import com.study.query_creator.common.util.auditlog.AuditLog;
import com.study.query_creator.common.util.invokelog.InvokeLog;
import com.study.query_creator.common.util.timeprofiler.TimeProfiler;
import com.study.query_creator.model.JsonResult;
import com.study.query_creator.model.RequestCreateStatusBean;
import com.study.query_creator.model.SsoUser;
import com.study.query_creator.request.RequestCreateStatusEnum;
import com.study.query_creator.request.Result;
import com.study.query_creator.service.RequestCreateStatusService;
import com.study.query_creator.web.aop.CommonForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class RequestController {
    @Autowired
    RequestCreateStatusService requestCreateStatusService;

    @ResponseBody
    @RequestMapping(value = "/requestsByStatus/{status}", method = RequestMethod.GET)
    public JsonResult getUserList(@PathVariable("status") String status) {
        Result result = Result.builder().build();
        try {
            List<RequestCreateStatusBean> requestCreateStatusBeans = requestCreateStatusService.getRequestByStatus(Integer.parseInt(status));
            return JsonResult.buildSuccessResult(requestCreateStatusBeans);
        } catch (Exception e ) {
            return JsonResult.buildFailResult(-1, "failed", null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/requestsByTypeAndStatus/{request_type}/{status}", method = RequestMethod.GET)
    public JsonResult getUserListByTypeAndStatus(@PathVariable("request_type")String requestType, @PathVariable("status") String status) {
        try {
            List<RequestCreateStatusBean> requestCreateStatusBeans = requestCreateStatusService.getRequestByTypeAndStatus(requestType, Integer.parseInt(status));
            return JsonResult.buildSuccessResult(requestCreateStatusBeans);
        } catch (Exception e ) {
            return JsonResult.buildFailResult(-1, "failed", null);
        }
    }

    @AuditLog
    @InvokeLog
    @TimeProfiler
    @ResponseBody
    @RequestMapping(value = "/add/{requestId}/{requestType}", method = RequestMethod.POST)
    public JsonResult add(@PathVariable("requestId") String reqId, @PathVariable("requestType")String requestType, @CommonForm SsoUser ssoUser) {
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
                return JsonResult.buildFailResult(-1, "failed", null);
            }
            else {
                return JsonResult.buildSuccessResult("succeed");
            }
        } catch (Exception e ) {
            log.error("failed..", e);
            return JsonResult.buildFailResult(-1, "failed", null);
        }

    }

}
