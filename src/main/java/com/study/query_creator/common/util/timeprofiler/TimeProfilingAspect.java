package com.study.query_creator.common.util.timeprofiler;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author 
 */
@Slf4j
@Aspect
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class TimeProfilingAspect {

    @Pointcut("@annotation(com.study.query_creator.common.util.timeprofiler.TimeProfiler)")
    private void timeProfilingPointCut() {}

    @Around("timeProfilingPointCut()")
    public Object profiling(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        try {
            return pjp.proceed();
        } finally {
            stopWatch.stop();
            log.info("execute time profiler: ###{}.{}, {}ms",methodSignature.getMethod().getDeclaringClass().getSimpleName(), methodSignature.getMethod().getName(), stopWatch.getTotalTimeMillis());
        }
    }

}
