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

@Component("JuejinHotProcessor")
public class JuejinHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(JuejinHotProcessor.class);

    @Autowired
    private BaseHotProcessor baseHotProcessor;

    public static final String DOMAIN = "https://juejin.im";
    public static final String HOT_PAGE_URL = "https://juejin.im";
    public static final String HOT_API_URL = "https://web-api.juejin.im/query";
    public static final String REQUEST_BODY = "{\"operationName\":\"\",\"query\":\"\",\"variables\":{\"first\":20,\"after\":\"\",\"order\":\"POPULAR\"},\"extensions\":{\"query\":{\"id\":\"21207e9ddb1de777adeaca7a2fb38030\"}}}";

    @Override
    public List<Info> crawlHotList()
    {
        List<Info> list = new ArrayList<>();

        // json
        String json = null;
        try
        {
            json = Jsoup.connect(HOT_API_URL).ignoreContentType(true).headers(getHeaders()).requestBody(REQUEST_BODY).method(Connection.Method.POST).execute().body();
        }
        catch (IOException e)
        {
            log.error("Something error {}", e.getMessage(), e);
        }

        if (json == null)
        {
            return list;
        }

        list = getResultList(json);
        return baseHotProcessor.handleData(list);
    }

    private Map<String,String> getHeaders()
    {
        Map<String,String> headers = new HashMap<>();

        headers.put("Content-Type", "application/json");
        headers.put("Host", "web-api.juejin.im");
        headers.put("Origin","https://juejin.im");
        headers.put("Referer", "https://juejin.im/?sort=popular");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0");
        headers.put("X-Agent", "Juejin/Web");
        return headers;
    }

    private List<Info> getResultList(String json)
    {
        List<Info> list = new ArrayList<>();
        if (json != null && json.length() > 0)
        {
            List<String> titles = JsonPath.read(json, "$.data.articleFeed.items.edges.[*].node.title");
            List<String> urls = JsonPath.read(json, "$.data.articleFeed.items.edges.[*].node.originalUrl");
            List<Info> indexInfoList = baseHotProcessor.getInfoListByTitlesAndUrls(titles, urls);
            list.addAll(indexInfoList);
            log.debug("index infoList size is {}", indexInfoList.size());
        }
        return list;
    }
}
