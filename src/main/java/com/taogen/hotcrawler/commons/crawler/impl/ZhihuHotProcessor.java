package com.taogen.hotcrawler.commons.crawler.impl;

import com.jayway.jsonpath.JsonPath;
import com.taogen.hotcrawler.api.constant.SiteProperties;
import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("ZhihuHotProcessor")
public class ZhihuHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(ZhihuHotProcessor.class);

    @Autowired
    private SiteProperties siteProperties;

    @Autowired
    private BaseHotProcessor baseHotProcessor;

    private String DOMAIN;
    private String HOT_API_URL;
    private String ITEM_KEY;

    @PostConstruct
    public void init()
    {
        SiteProperties.SiteInfo siteInfo = siteProperties.findByProcessorName(this.getClass().getSimpleName());
        this.DOMAIN = siteInfo.getDomain();
        this.HOT_API_URL = siteInfo.getHotApiUrl();
        this.ITEM_KEY = siteInfo.getItemKey();
    }

    @Override
    public List<Info> crawlHotList()
    {
        List<Info> list = new ArrayList<>();
        // login

        // json by API
        String json = baseHotProcessor.getJson(HOT_API_URL, log);
        if (json == null)
        {
            return list;
        }

        // items
        List<String> titles = JsonPath.read(json, "$.data.[*].target.title");
        List<String> urls = JsonPath.read(json, "$.data.[*].target.url");
        log.debug("elements size: " + titles.size());

        for (int i = 0; i < urls.size(); i++)
        {
            urls.set(i, urls.get(i).replace("https://api.zhihu.com/questions", DOMAIN + "/question"));
        }

        for (int i = 1; i < titles.size(); i++)
        {
            list.add(new Info(String.valueOf(i), titles.get(i), urls.get(i)));
        }

        log.debug("return list size: " + list.size());
        return list;
    }
}
