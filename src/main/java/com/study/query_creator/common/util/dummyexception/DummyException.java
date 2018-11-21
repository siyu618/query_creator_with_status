package com.study.query_creator.common.util.dummyexception;

import java.lang.annotation.*;

/**
 * @Description: 被该注解标注的方法，执行异常的时候会忽略异常
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DummyException {
}
