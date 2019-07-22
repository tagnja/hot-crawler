package com.taogen.hotcrawler.frontend.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController
{

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("message", "hello");
        return "index"; //view
    }
}
