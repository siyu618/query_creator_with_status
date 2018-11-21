package com.study.query_creator.common.util.auditlog;


import com.study.query_creator.common.util.DateUtils;
import com.study.query_creator.common.util.IpUtils;
import com.study.query_creator.common.util.JsonUtils;
import com.study.query_creator.config.SsoProperties;
import com.study.query_creator.model.SsoUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class AuditLogAspect {
    private static final String SYSTEM = "system";

    @Pointcut("@annotation(com.study.query_creator.common.util.auditlog.AuditLog)")
    public void operationLogPointCut() {
    }

    @AfterReturning(returning = "result", pointcut = "operationLogPointCut()")
    public void doAfterReturningOperator(JoinPoint joinPoint, Object result) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        // TODO: hack here
        SsoUser ssoUser = (SsoUser) request.getSession().getAttribute(SsoProperties.SESSION_USER);
        if (Objects.isNull(ssoUser)) {
            ssoUser = SsoUser.builder().traceId("traceId").userName("system").build();
        }

        if (Objects.isNull(ssoUser)) {
            log.error("the request.userInfo is null,request:{},objectResult:{}", request, result);
            return;
        }

        if (Objects.isNull(result)) {
            log.error("doAfterReturningOperator.objectResult is null,request:{}", request);
            return;
        }


        try {
            boolean isPost = request.getMethod().equalsIgnoreCase(RequestMethod.POST.toString());
            String postParams = null;
            String getParams = null;
            if (isPost) {
                postParams = JsonUtils.toString(request.getParameterMap());
            } else {
                getParams = JsonUtils.toString(request.getParameterMap());
            }
            String resultStr = JsonUtils.toString(result);

            AuditLogBean audit = AuditLogBean.builder()
                    .system(SYSTEM)
                    .hostIp(IpUtils.getLocalIp())
                    .userName(ssoUser.getUserName())
                    .url(request.getRequestURL().toString())
                    .getParams(getParams)
                    .postParams(postParams)
                    .userIp(IpUtils.getRemoteIpAddress(request))
                    .timestamp(LocalDateTime.now().format(DateUtils.NORMAL_DATE_FORMATTER))
                    .response(resultStr)
                    .traceId(ssoUser.getTraceId())
                    .result(response.getStatus())
                    .build();
            log.info("{}", audit.toAuditLog());
        } catch (Exception e) {
            log.error("doAfterReturningOperator error user:{} joinPoint:{} joinResult:{}", ssoUser, joinPoint, result, e);
        }
    }
}
