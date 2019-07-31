package com.taogen.hotcrawler.api.web.controller;

import com.taogen.hotcrawler.api.exception.DataNotFoundException;
import com.taogen.hotcrawler.api.web.model.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractApiController
{
    protected static final Logger log = LoggerFactory.getLogger(AbstractApiController.class);

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({DataNotFoundException.class})
    @Order(Integer.MIN_VALUE)
    public ResponseModel dataNotFoundExceptionHandler(HttpServletRequest request)
    {
        return getResponseModel(/*request, */404, "无法找到指定的数据。");
    }

    private ResponseModel getResponseModel(/*HttpServletRequest request,*/ int code, String message)
    {
        ResponseModel result = new ResponseModel();
        result.setErrCode(Integer.valueOf(code));
        result.setErrMsg(message);
        return result;
    }
}
