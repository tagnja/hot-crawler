package com.taogen.hotcrawler.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController
{
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test()
    {
        return "this is a test!";
    }
}
