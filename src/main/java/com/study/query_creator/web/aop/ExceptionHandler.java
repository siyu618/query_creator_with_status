package com.study.query_creator.web.aop;

import com.study.query_creator.model.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler
    public JsonResult processException(Exception ex) {
        logger.error(ex.getMessage(), ex);

        JsonResult jsonResult;
        if (ex instanceof BindException) {
            BindException c = (BindException) ex;
            List<FieldError> errors = c.getBindingResult().getFieldErrors();
            StringBuilder errorMsg = new StringBuilder();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(":").append(error.getDefaultMessage()).append(";");
            }
            jsonResult = JsonResult.buildFailResult(-2, errorMsg.toString(), null);

        } else {
            jsonResult = JsonResult.buildFailResult(-1, "failed", null);
        }

        return jsonResult;
    }
}
