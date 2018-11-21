package com.study.query_creator.common.util.timeprofiler;

import java.lang.annotation.*;

/**
 * @Description: 被该注解标注的方法，会在日志中记录执行时间，
 *  仅对在同一个线程或fork/join的线程组的执行时间生效，若方法内部开启异步线程，无法记录到
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeProfiler {
}
