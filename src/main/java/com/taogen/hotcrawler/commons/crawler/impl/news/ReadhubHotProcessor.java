package com.taogen.hotcrawler.commons.crawler.impl.news;

import com.jayway.jsonpath.JsonPath;
import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.constant.RequestMethod;
import com.taogen.hotcrawler.commons.crawler.APIHotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("ReadhubHotProcessor")
public class ReadhubHotProcessor extends APIHotProcessor
{
    @Autowired
    private SiteProperties siteProperties;

    @Autowired
    private ApplicationContext context;

    @Override
    @PostConstruct
    protected void initialize(){
        RequestMethod requestMethod = RequestMethod.GET;
        setFieldsByProperties(siteProperties, requestMethod, generateHeader(),generateRequestBody());
        injectBeansByContext(context);
        setLog(LoggerFactory.getLogger(getClass()));
    }

    @Override
    protected List<Info> getInfoDataByJson(String json) {
        List<Info> list = new ArrayList<>();
        List<String> titles = JsonPath.read(json, "$.data.[*].title");
        List<String> urls = JsonPath.read(json, "$.data.[*].id");

        for (int i = 0; i < urls.size(); i++)
        {
            urls.set(i, new StringBuilder(this.prefix).append("/").append(urls.get(i)).toString());
        }

        for (int i = 0; i < titles.size(); i++)
        {
            list.add(new Info(String.valueOf(i), titles.get(i), urls.get(i)));
        }
        return list;
    }

    @Override
    protected Map<String, String> generateHeader() {
        return null;
    }

    @Override
    protected String generateRequestBody() {
        return null;
    }
}
