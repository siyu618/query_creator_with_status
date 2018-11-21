package com.study.query_creator.web.aop;


import com.study.query_creator.config.SsoProperties;
import com.study.query_creator.model.SsoUser;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
public class CommonParamsResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(SsoUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        if (parameter.hasParameterAnnotation(CommonForm.class)) {
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
            SsoUser ssoUser = getParam(request, SsoProperties.USER_ID);
            
            String traceId = request.getHeader(SsoProperties.TRACE_ID);
            if (ssoUser!=null){
                ssoUser.setTraceId(traceId);
            }


            return ssoUser;
        } else {
            return WebArgumentResolver.UNRESOLVED;
        }

    }

    @SuppressWarnings("unchecked")
    private <T> T getParam(final HttpServletRequest request, String key) {
        Object o = request.getSession().getAttribute(key);
        if(null != o) {
            return (T)o;
        }
        return null;
    }

}