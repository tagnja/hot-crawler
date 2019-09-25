package com.taogen.hotcrawler.commons.crawler.impl.technique;

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

@Component("DzoneHotProcessor")
public class DzoneHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(DzoneHotProcessor.class);

    @Autowired
    private BaseHotProcessor baseHotProcessor;

    public static final String DOMAIN = "https://dzone.com";
    public static final String HOT_PAGE_URL = "https://dzone.com/list";
    public static final String HOT_API_URL = "https://dzone.com/services/widget/article-listV2/list?sort=newest";
    public static final String ITEM_KEY = "article-title";

    @Override
    public List<Info> crawlHotList()
    {
        List<Info> list = new ArrayList<>();

        // API Json
        String json = null;
        json = baseHotProcessor.getJson(HOT_API_URL, log);

        if (json == null)
        {
            return list;
        }

        list = getResultList(json);
        return baseHotProcessor.handleData(list);
    }

    private List<Info> getResultList(String json)
    {
        List<Info> list = new ArrayList<>();

        if (json != null && json.length() > 0)
        {
            List<String> titles = JsonPath.read(json, "$.result.data.nodes.[*].title");
            List<String> urls = JsonPath.read(json, "$.result.data.nodes.[*].articleLink");
            urls = baseHotProcessor.urlsAddPrefix(DOMAIN, urls);
            List<Info> indexInfoList = baseHotProcessor.getInfoListByTitlesAndUrls(titles, urls);
            list.addAll(indexInfoList);
            log.debug("index infoList size is {}", indexInfoList.size());
        }

        return list;
    }
}
