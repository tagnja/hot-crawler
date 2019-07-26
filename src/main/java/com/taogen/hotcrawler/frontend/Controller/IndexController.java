package com.taogen.hotcrawler.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController
{
    Logger log = LoggerFactory.getLogger(IndexController.class);

    @Value("${domain}")
    private String domain;

    @GetMapping("/")
    public String toIndexPage(Model model)
    {
        log.debug("Go to index page: " + domain);
        model.addAttribute("domain", domain);
        return "index"; //view
    }
}
