package com.taogen.hotcrawler.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("InfoController")
@RequestMapping("/info")
public class InfoController
{
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String test()
    {
        return "{ \"ret_code\": 0, \"ret_msg\": \"ok\"}";
    }
}
