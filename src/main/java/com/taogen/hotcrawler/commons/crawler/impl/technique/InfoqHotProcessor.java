package com.taogen.hotcrawler.commons.crawler.impl.technique;


import com.jayway.jsonpath.JsonPath;
import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.constant.RequestMethod;
import com.taogen.hotcrawler.commons.crawler.AbstractHotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("InfoqHotProcessor")
public class InfoqHotProcessor extends AbstractHotProcessor
{
    public static final String HOT_API_URL_INDEX = "https://www.infoq.cn/public/v1/article/getIndexList";
    public static final String HOT_API_URL_RECOMMEND = "https://www.infoq.cn/public/v1/my/recommond";
    public static final String ARTICLE_PREFIX = "https://www.infoq.cn/article/";
    public static final String REQUEST_BODY = "{\"size\":12}";

    private String indexJson;
    private String recommendJson;
    private String recommendJson2;
    private Long score;

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
    public List<Info> crawlHotList() {
        getJson();
        List<Info> list = getInfoDataByJson();
        log.debug("crawl hot list from {}, list size is {}", this.name, list.size());
        return handlerCenter.handleData(list);
    }

    protected void getJson(){
        try {
            // selected 4 + recommend 24 + hot_day 8
            indexJson = Jsoup.connect(HOT_API_URL_INDEX).ignoreContentType(true).
                    headers(getHttpRequest().getHeader()).method(Connection.Method.GET).execute().body();
            recommendJson = Jsoup.connect(HOT_API_URL_RECOMMEND).ignoreContentType(true).
                    headers(getHttpRequest().getHeader()).requestBody(REQUEST_BODY).method(Connection.Method.POST).execute().body();
            this.score = JsonPath.read(recommendJson, "$.data.[-1].score");
            if (score != null && score > 0)
            {
                recommendJson2 = Jsoup.connect(HOT_API_URL_RECOMMEND).ignoreContentType(true).
                        headers(getHttpRequest().getHeader()).requestBody(getHttpRequest().getRequestBody()).method(Connection.Method.POST).execute().body();
            }
        }
        catch (IOException e)
        {
            log.error("Something error {}", e.getMessage(), e);
        }
    }

    protected List<Info> getInfoDataByJson() {
        List<Info> list = new ArrayList<>();
        if (indexJson != null && indexJson.length() > 0)
        {
            List<String> titles = JsonPath.read(indexJson, "$.data.recommend_list.[*].article_sharetitle");
            List<String> urls = JsonPath.read(indexJson, "$.data.recommend_list.[*].uuid");
            urls = urlsAddPrefix(ARTICLE_PREFIX, urls);
            List<Info> indexInfoList = getInfoListByTitlesAndUrls(titles, urls);
            list.addAll(indexInfoList);
            log.debug("index infoList size is {}", indexInfoList.size());
        }
        if (recommendJson != null && recommendJson.length() > 0)
        {
            List<String> titles = JsonPath.read(recommendJson, "$.data.[*].article_sharetitle");
            List<String> urls = JsonPath.read(recommendJson, "$.data.[*].uuid");
            urls = urlsAddPrefix(ARTICLE_PREFIX, urls);
            List<Info> recommend1InfoList = getInfoListByTitlesAndUrls(titles, urls);
            list.addAll(recommend1InfoList);
            log.debug("recommend infoList size is {}", recommend1InfoList.size());
        }
        if (recommendJson2 != null && recommendJson2.length() > 0)
        {
            List<String> titles = JsonPath.read(recommendJson2, "$.data.[*].article_sharetitle");
            List<String> urls = JsonPath.read(recommendJson2, "$.data.[*].uuid");
            urls = urlsAddPrefix(ARTICLE_PREFIX, urls);
            List<Info> recommend2InfoList = getInfoListByTitlesAndUrls(titles, urls);
            list.addAll(recommend2InfoList);
            log.debug("recommend2 infoList size is {}", recommend2InfoList.size());
        }
        if (indexJson != null && indexJson.length() > 0)
        {
            List<String> titles = JsonPath.read(indexJson, "$.data.hot_day_list.[*].article_sharetitle");
            List<String> urls = JsonPath.read(indexJson, "$.data.hot_day_list.[*].uuid");
            urls = urlsAddPrefix(ARTICLE_PREFIX, urls);
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

    @Override
    protected Map<String, String> generateHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("Accept", "application/json, text/plain, */*");
        header.put("Content-Type", "application/json");
        header.put("Referer", "https://www.infoq.cn/");
        header.put("Sec-Fetch-Mode", "cors");
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
        return header;
    }

    @Override
    protected String generateRequestBody() {
        if (score != null && score > 0)
        {
            return "{\"size\":12, \"score\":" + score+ "}";
        }
        else
        {
            return null;
        }
    }
}