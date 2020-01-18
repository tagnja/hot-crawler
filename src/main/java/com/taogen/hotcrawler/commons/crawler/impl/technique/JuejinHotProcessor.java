package com.taogen.hotcrawler.commons.crawler.impl.technique;

import com.jayway.jsonpath.JsonPath;
import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.constant.RequestMethod;
import com.taogen.hotcrawler.commons.crawler.APIHotProcessor;
import com.taogen.hotcrawler.commons.crawler.SimpleAPIHotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component("JuejinHotProcessor")
public class JuejinHotProcessor extends SimpleAPIHotProcessor
{

    @Autowired
    private SiteProperties siteProperties;

    @Autowired
    private ApplicationContext context;

    @Override
    @PostConstruct
    protected void initialize(){
        RequestMethod requestMethod = RequestMethod.POST;
        setFieldsByProperties(siteProperties, requestMethod, generateHeader(),generateRequestBody());
        injectBeansByContext(context);
        setLog(LoggerFactory.getLogger(getClass()));
        setTitleJsonPaths(Arrays.asList("$.data.articleFeed.items.edges.[*].node.title"));
        setUrlJsonPaths(Arrays.asList("$.data.articleFeed.items.edges.[*].node.originalUrl"));
    }

    @Override
    protected Map<String, String> generateHeader() {
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Host", "web-api.juejin.im");
        header.put("Origin","https://juejin.im");
        header.put("Referer", "https://juejin.im/?sort=popular");
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0");
        header.put("X-Agent", "Juejin/Web");
        return header;
    }

    @Override
    protected String generateRequestBody() {
        return "{\"operationName\":\"\",\"query\":\"\",\"variables\":{\"first\":20,\"after\":\"\",\"order\":\"POPULAR\"},\"extensions\":{\"query\":{\"id\":\"21207e9ddb1de777adeaca7a2fb38030\"}}}";
    }
}
