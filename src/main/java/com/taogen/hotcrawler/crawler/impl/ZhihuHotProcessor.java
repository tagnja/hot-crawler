package com.taogen.hotcrawler.crawler.impl;

import com.jayway.jsonpath.JsonPath;
import com.taogen.hotcrawler.crawler.HotProcess;
import com.taogen.hotcrawler.entity.Info;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZhihuHotProcessor implements HotProcess
{
    public static final String ZHIHU_DOMAIN = "https://zhihu.com";
//    public static final String HOT_URL = "https://zhihu.com/hot";
//    public static final String ITEM_KEY = "HotItem";
    public static final String HOT_API = "https://www.zhihu.com/api/v3/feed/topstory/hot-lists/total?limit=50&desktop=true";

    @Override
    public List<Info> getHotList()
    {
        List<Info> list = new ArrayList<>();
        // login

        // json by API
        String json = null;
        try {
            json = Jsoup.connect(HOT_API).ignoreContentType(true).execute().body();
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
