package com.taogen.hotcrawler;

import io.swagger.annotations.Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication
@Api(description = "This is a hello API")
public class App
{
    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
    }
}
