package com.taogen.hotcrawler.api.web.model;

import lombok.Data;

@Data
public class ResponseModel
{
    private static final long serialVersionUID = 5413727785722549217L;
    protected Integer errCode;
    protected String errMsg;
//    protected String requestId;
}
