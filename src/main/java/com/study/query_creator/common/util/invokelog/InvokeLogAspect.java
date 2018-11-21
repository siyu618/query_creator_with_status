package com.study.query_creator.common.util.invokelog;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Slf4j
@Aspect
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE + 2)
public class InvokeLogAspect {

    private static final ThreadLocal<String> INVOKE_TRACE_ID = new ThreadLocal<>();

    private static final String VOID = "void";

    @Pointcut("@annotation(com.study.query_creator.common.util.invokelog.InvokeLog)")
    private void invokeLogPointcut() {
    }

    @Around("invokeLogPointcut()")
    public Object methodInvokeLog(ProceedingJoinPoint pjp) throws Throwable {


        String invokeTraceId = INVOKE_TRACE_ID.get();
        if (StringUtils.isBlank(invokeTraceId)) {
            invokeTraceId = UUID.randomUUID().toString();
            INVOKE_TRACE_ID.set(invokeTraceId);
        }

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Object target = pjp.getTarget();

        String className = target.getClass().getTypeName();
        String methodName = methodSignature.getName();
        String paramTypeList = Joiner.on(",")
                .join(
                        ImmutableList.copyOf(methodSignature.getParameterTypes())
                                .stream()
                                .map(Class::getTypeName)
                                .toArray());
        String methodInfo = className.concat("#").concat(methodName).concat("(").concat(paramTypeList).concat(")");

        Object[] args = pjp.getArgs();
        log.info("method invoke before log,invokeTraceId:[{}],Method : [{}],Args : [{}]", invokeTraceId, methodInfo, args);

        Object res = pjp.proceed();

        String returnType = methodSignature.getReturnType().getName();
        if (StringUtils.isBlank(returnType) || VOID.equals(returnType.toLowerCase().trim())) {
            log.info("method invoke after log,invokeTraceId:[{}],Method : [{}],return type is void", invokeTraceId, methodInfo);
        } else {
            log.info("method invoke after log,invokeTraceId:[{}],Method : [{}],return type :[{}],return value:[{}]", invokeTraceId, methodInfo, returnType, res);
        }

        return res;
    }
}
