package com.taogen.hotcrawler.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("InfoController")
@RequestMapping("/api/v1")
public class InfoController
{
    public static final String PRODUCES_JSON = "application/json;charset=UTF-8";

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = PRODUCES_JSON)
    public String test()
    {
        return "{ \"ret_code\": 0, \"ret_msg\": \"ok\"}";
    }

    @RequestMapping(value = "/types", method = RequestMethod.GET, produces = PRODUCES_JSON)
    public String getTypes()
    {
        return null;
    }

    @RequestMapping(value = "/type/{id}/infos", method = RequestMethod.GET, produces = PRODUCES_JSON)
    public String getTypeInfos(@PathVariable(value = "id") String id)
    {
        return null;
    }
}
