package com.taogen.hotcrawler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 */
@RestController
@SpringBootApplication
@Api(description = "This is a hello API")
public class App
{
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation("Just return hello")
    String home()
    {
        String str = "Hello world by hotcrawler! ";
        return str;
    }

    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
    }
}
