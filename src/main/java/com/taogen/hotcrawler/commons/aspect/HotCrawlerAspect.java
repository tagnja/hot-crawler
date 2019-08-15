package com.taogen.hotcrawler.commons.aspect;


import com.taogen.hotcrawler.commons.entity.Info;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Aspect
@Configuration
public class HotCrawlerAspect
{
    private Logger log = LoggerFactory.getLogger(HotCrawlerAspect.class);

    @Pointcut("execution(* com.taogen.hotcrawler.commons.crawler.impl.*.*.crawlHotList(..))")
    public void crawlerPointCut()
    {
        // define PointCut not need method body
    }

    @Before("crawlerPointCut()")
    public void before(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().toShortString();
        log.debug("<<< Begin execute {}", methodName);
    }

    @AfterReturning(pointcut = "crawlerPointCut()", returning="retVal")
    public void afterReturnAdvice(JoinPoint joinPoint, List<Info> retVal)
    {
        String methodName = joinPoint.getSignature().toShortString();
        log.debug("{} return list size is {}", methodName, retVal != null ? retVal.size() : retVal);
    }

    @Around("crawlerPointCut()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().toShortString();
        long start = System.currentTimeMillis();
        Object output = pjp.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        log.debug(">>> {} execution time: {} milliseconds.", methodName, elapsedTime);
        return output;
    }
}
