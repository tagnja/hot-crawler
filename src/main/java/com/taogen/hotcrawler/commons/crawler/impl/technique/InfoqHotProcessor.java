package com.taogen.hotcrawler.commons.crawler.impl.technique;


import com.jayway.jsonpath.JsonPath;
import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.crawler.impl.BaseHotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("InfoqHotProcessor")
public class InfoqHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(InfoqHotProcessor.class);

    @Autowired
    private BaseHotProcessor baseHotProcessor;

    public static final String DOMAIN = "https://www.infoq.cn";
    public static final String HOT_PAGE_URL = "https://www.infoq.cn";
    public static final String ITEM_KEY = "com-article-title";
    public static final String HOT_API_URL_INDEX = "https://www.infoq.cn/public/v1/article/getIndexList";
    public static final String HOT_API_URL_RECOMMEND = "https://www.infoq.cn/public/v1/my/recommond";
    public static final String ARTICLE_PREFIX = "https://www.infoq.cn/article/";
    public static final String REQUEST_BODY = "{\"size\":12}";

    @Override
    public List<Info> crawlHotList()
    {
        List<Info> list = new ArrayList<>();

        // json by API
        String indexJson = null;
        String recommendJson = null;
        String recommendJson2 = null;
        try {

            // selected 4 + recommend 24 + hot_day 8
            indexJson = Jsoup.connect(HOT_API_URL_INDEX).ignoreContentType(true).headers(getHeaders()).method(Connection.Method.GET).execute().body();
            recommendJson = Jsoup.connect(HOT_API_URL_RECOMMEND).ignoreContentType(true).headers(getHeaders()).requestBody(REQUEST_BODY).method(Connection.Method.POST).execute().body();
            Long score = JsonPath.read(recommendJson, "$.data.[-1].score");
            if (score != null && score > 0)
            {
                recommendJson2 = Jsoup.connect(HOT_API_URL_RECOMMEND).ignoreContentType(true).headers(getHeaders()).requestBody(getBody(score)).method(Connection.Method.POST).execute().body();
            }
            list = getResultList(indexJson, recommendJson, recommendJson2);
        }
        catch (IOException e)
        {
            log.error("Something error {}", e.getMessage(), e);
        }

        return baseHotProcessor.handleData(list);
    }

    private Map getHeaders()
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json, text/plain, */*");
        headers.put("Content-Type", "application/json");
        headers.put("Referer", "https://www.infoq.cn/");
        headers.put("Sec-Fetch-Mode", "cors");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
        return headers;
    }

    private String getBody(Long score)
    {
        if (score != null && score > 0)
        {
            return "{\"size\":12, \"score\":" + score+ "}";
        }
        else
        {
            return null;
        }
    }

    private List<Info> getResultList(String indexJson, String recommendJson, String recommendJson2)
    {
        List<Info> list = new ArrayList<>();
        if (indexJson != null && indexJson.length() > 0)
        {
            List<String> titles = JsonPath.read(indexJson, "$.data.recommend_list.[*].article_sharetitle");
            List<String> urls = JsonPath.read(indexJson, "$.data.recommend_list.[*].uuid");
            List<Info> indexInfoList = getInfoListByTitlesAndUrls(titles, urls);
            list.addAll(indexInfoList);
            log.debug("index infoList size is {}", indexInfoList.size());
        }
        if (recommendJson != null && recommendJson.length() > 0)
        {
            List<String> titles = JsonPath.read(recommendJson, "$.data.[*].article_sharetitle");
            List<String> urls = JsonPath.read(recommendJson, "$.data.[*].uuid");
            List<Info> recommend1InfoList = getInfoListByTitlesAndUrls(titles, urls);
            list.addAll(recommend1InfoList);
            log.debug("recommend infoList size is {}", recommend1InfoList.size());
        }
        if (recommendJson2 != null && recommendJson2.length() > 0)
        {
            List<String> titles = JsonPath.read(recommendJson2, "$.data.[*].article_sharetitle");
            List<String> urls = JsonPath.read(recommendJson2, "$.data.[*].uuid");
            List<Info> recommend2InfoList = getInfoListByTitlesAndUrls(titles, urls);
            list.addAll(recommend2InfoList);
            log.debug("recommend2 infoList size is {}", recommend2InfoList.size());
        }
        if (indexJson != null && indexJson.length() > 0)
        {
            List<String> titles = JsonPath.read(indexJson, "$.data.hot_day_list.[*].article_sharetitle");
            List<String> urls = JsonPath.read(indexJson, "$.data.hot_day_list.[*].uuid");
            List<Info> hotInfoList = getInfoListByTitlesAndUrls(titles, urls);
            list.addAll(hotInfoList);
            log.debug("day hot infoList size is {}", hotInfoList.size());
        }

        // update all id
        for (int i = 0; i < list.size(); i++)
        {
            list.get(i).setId(String.valueOf(i+1));
        }

        return list;
    }

    private List<Info> getInfoListByTitlesAndUrls(List<String> titles, List<String> urls)
    {
        List<Info> infoList = new ArrayList<>();
        if (titles == null || urls == null)
        {
            return infoList;
        }
        for (int i = 0; i < urls.size(); i++)
        {
            urls.set(i, ARTICLE_PREFIX + urls.get(i));
        }

        for (int i = 0; i < titles.size(); i++)
        {

            infoList.add(new Info(String.valueOf(i+1), titles.get(i), urls.get(i)));
        }
        return infoList;
    }
}
