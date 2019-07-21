package com.taogen.hotcrawler.crawler.impl;

import com.jayway.jsonpath.JsonPath;
import com.taogen.hotcrawler.crawler.HotProcess;
import com.taogen.hotcrawler.entity.Info;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ZhihuHotProcessor implements HotProcess
{
    private static final Logger log = LoggerFactory.getLogger(ZhihuHotProcessor.class);

    @Value("${crawler.site.zhihu.domain}")
    private String ZHIHU_DOMAIN;

    @Value("${crawler.site.zhihu.hotApiUrl}")
    private String HOT_API_URL;

    @Override
    public List<Info> getHotList()
    {
        List<Info> list = new ArrayList<>();
        // login

        // json by API
        String json = null;
        try {
            json = Jsoup.connect(HOT_API_URL).ignoreContentType(true).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // items
        List<String> titles = JsonPath.read(json, "$.data.[*].target.title");
        List<String> urls = JsonPath.read(json, "$.data.[*].target.url");
        for (int i = 0; i < urls.size(); i++)
        {
            urls.set(i, urls.get(i).replace("https://api.zhihu.com/questions", ZHIHU_DOMAIN + "/question"));
        }
        for (int i = 1; i < titles.size(); i++)
        {
            list.add(new Info(String.valueOf(i), titles.get(i), urls.get(i)));
        }

        return list;
    }
}
