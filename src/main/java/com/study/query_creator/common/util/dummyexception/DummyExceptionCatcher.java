package com.study.query_creator.common.util.dummyexception;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 */
@Slf4j
@Aspect
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE+1)
public class DummyExceptionCatcher {

    @Pointcut("@annotation(com.study.query_creator.common.util.dummyexception.DummyException)")
    private void dummyExceptionPointcut() {
    }

    @Around("dummyExceptionPointcut()")
    public Object catchEx(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            log.error("Dummy Exception", e);
        }
        return null;
    }
}