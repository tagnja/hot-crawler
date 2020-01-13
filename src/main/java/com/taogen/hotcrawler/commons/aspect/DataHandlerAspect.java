package com.taogen.hotcrawler.commons.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class DataHandlerAspect
{
    private Logger log = LoggerFactory.getLogger(DataHandlerAspect.class);

    @Pointcut("execution(* com.taogen.hotcrawler.commons.crawler.handler.*.handleRequest(..))")
    public void dataHandlerPointCut()
    {
        // define PointCut not need method body
    }

    @After("dataHandlerPointCut()")
    public void after(JoinPoint joinPoint)
    {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        log.debug("Data handle by {}", className);
    }


}
