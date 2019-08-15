package com.taogen.hotcrawler.commons.crawler.impl.slack;

import com.jayway.jsonpath.JsonPath;
import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.crawler.impl.BaseHotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("ZhihuHotProcessor")
public class ZhihuHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(ZhihuHotProcessor.class);

    @Autowired
    private BaseHotProcessor baseHotProcessor;

    public static final String DOMAIN = "https://zhihu.com";
    public static final String HOT_API_URL = "https://www.zhihu.com/api/v3/feed/topstory/hot-lists/total?limit=50&desktop=true";

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

        for (int i = 0; i < urls.size(); i++)
        {
            urls.set(i, urls.get(i).replace("https://api.zhihu.com/questions", DOMAIN + "/question"));
        }

        for (int i = 1; i < titles.size(); i++)
        {
            list.add(new Info(String.valueOf(i), titles.get(i), urls.get(i)));
        }

        return baseHotProcessor.handleData(list);
    }
}
