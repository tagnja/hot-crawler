package com.taogen.hotcrawler.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController
{
    Logger log = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/")
    public String toIndexPage()
    {
        return "index"; //view
    }
}
