package com.taogen.hotcrawler.commons.crawler.impl.news;

import com.jayway.jsonpath.JsonPath;
import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.crawler.impl.BaseHotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("ReadhubHotProcessor")
public class ReadhubHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(ReadhubHotProcessor.class);

    @Autowired
    private BaseHotProcessor baseHotProcessor;
    public static final String API_URL = "https://api.readhub.cn/topic?lastCursor=&pageSize=20";
    public static final String ITEM_URL_PREFIX = "https://readhub.cn/topic";

    @Override
    public List<Info> crawlHotList()
    {
        String json = baseHotProcessor.getJson(API_URL, log);
        List<Info> list = getInfoListByJson(json);
        return baseHotProcessor.handleData(list);
    }

    public List<Info> getInfoListByJson(String json)
    {
        List<Info> list = new ArrayList<>();

        if (json == null)
        {
            return list;
        }

        List<String> titles = JsonPath.read(json, "$.data.[*].title");
        List<String> urls = JsonPath.read(json, "$.data.[*].id");

        for (int i = 0; i < urls.size(); i++)
        {
            urls.set(i, new StringBuilder(ITEM_URL_PREFIX).append("/").append(urls.get(i)).toString());
        }

        for (int i = 0; i < titles.size(); i++)
        {
            list.add(new Info(String.valueOf(i), titles.get(i), urls.get(i)));
        }
        return list;
    }
}
