package com.taogen.hotcrawler;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import io.swagger.annotations.Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication
@Api("This is a hello API")
@EnableEncryptableProperties
public class App
{
    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
    }
}
