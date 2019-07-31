package com.taogen.hotcrawler.api.web.model.response;

import com.taogen.hotcrawler.api.web.model.ResponseModel;
import lombok.Data;

@Data
public class GenericResponseModel<T> extends ResponseModel
{
    private static final long serialVersionUID = 7100791756352030649L;
    private T data;

//    public GenericResponseModel(){}

//    public GenericResponseModel(String requestId)
//    {
//        this.requestId = requestId;
//    }
}
