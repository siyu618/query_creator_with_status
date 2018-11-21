package com.study.query_creator.web.aop;


import com.study.query_creator.config.SsoProperties;
import com.study.query_creator.model.SsoUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private RestTemplate restTemplate;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

        log.info("[LoginInterceptor]:{}", request.getRequestURI());

        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            LoginRequired ssoRequired = handlerMethod.getMethodAnnotation(LoginRequired.class);

            if (Objects.nonNull(ssoRequired)) {
                validateSso(request, response);
            }
            setUserCookie(request, response, "username");
        }
        return super.preHandle(request, response, handler);
    }

    private void validateSso(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter(SsoProperties.USER_ID);
        String traceId = request.getParameter(SsoProperties.TRACE_ID);

        try {
            SsoUser user = checkSsoCode(request, response);
            if (Objects.nonNull(user)) {
                return;
            }
            // get ssoUSer & save to session

            request.setAttribute("user", user);
        } catch (Exception e) {
            log.error("api interceptor error, api:" + request.getRequestURI(), e);
            throw new RuntimeException("Could not get sso user");
        }
    }

    /** redirect to sso control
     * @param request
     * @param response
     * @param currentUrl
     * @throws IOException
     */
    private void redirect(HttpServletRequest request, HttpServletResponse response, String currentUrl) throws IOException {
    }

    /**
     * get sso user from request
     * @param request
     * @param response
     * @return
     */
    private SsoUser checkSsoCode(HttpServletRequest request, HttpServletResponse response){
        return null;
    }

    /**
     * set cookie for user
     * @param request
     * @param response
     * @param username
     */
    private void setUserCookie(HttpServletRequest request, HttpServletResponse response, String username){
    }

}
