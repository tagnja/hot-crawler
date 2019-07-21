package com.taogen.hotcrawler.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("InfoController")
@RequestMapping("/api/v1")
@Api(description = "Information API")
public class InfoController
{
    private static final Logger log = LoggerFactory.getLogger(InfoController.class);
    public static final String PRODUCES_JSON = "application/json;charset=UTF-8";

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = PRODUCES_JSON)
    public String test()
    {
        return "{ \"ret_code\": 0, \"ret_msg\": \"ok\"}";
    }

    @RequestMapping(value = "/types", method = RequestMethod.GET, produces = PRODUCES_JSON)
    @ApiOperation("Get All Type of Information.")
    public String getTypes()
    {
        return "{ \"ret_code\": 0, \"ret_msg\": \"ok\"}";
    }

    @RequestMapping(value = "/type/{id}/infos", method = RequestMethod.GET, produces = PRODUCES_JSON)
    @ApiOperation("Get All Information of specified type.")
    public String getTypeInfos(@PathVariable(value = "id") String id)
    {
        return "{ \"ret_code\": 0, \"ret_msg\": \"ok\", \"type\":" + id +"}";
    }
}
