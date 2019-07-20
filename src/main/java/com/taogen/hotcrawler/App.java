package com.taogen.hotcrawler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



/**
 * Hello world!
 *
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
        return "Hello world by hotcrawler!";
    }

    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
    }
}
