package com.taogen.hotcrawler.commons.vo;

import com.taogen.hotcrawler.commons.constant.RequestMethod;
import lombok.Data;

import java.util.Map;

@Data
public class HttpRequest {
    private String url;
    private RequestMethod requestMethod;
    private Map<String, String> header;
    private String requestBody;
}
