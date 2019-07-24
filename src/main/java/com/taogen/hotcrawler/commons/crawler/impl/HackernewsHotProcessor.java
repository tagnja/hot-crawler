package com.taogen.hotcrawler.commons.crawler.impl;

import com.taogen.hotcrawler.api.constant.SiteProperties;
import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("HackernewsHotProcessor")
public class HackernewsHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(CloudmusicHotProcessor.class);

    @Autowired
    private SiteProperties siteProperties;

    private String DOMAIN;
    private String HOT_PAGE_URL;
    private String ITEM_KEY;

    @PostConstruct
    public void init()
    {
        SiteProperties.SiteInfo siteInfo = siteProperties.findByProcessorName(this.getClass().getSimpleName());
        this.DOMAIN = siteInfo.getDomain();
        this.HOT_PAGE_URL = siteInfo.getHotPageUrl();
        this.ITEM_KEY = siteInfo.getItemKey();
    }

    @Override
    public List<Info> crawlHotList() {
        List<Info> list = new ArrayList<>();

        // document
        Document doc = null;
        try {
            Connection connection = Jsoup.connect(HOT_PAGE_URL); //connect(HOT_PAGE_URL).get();
            connection.header("Host", "news.ycombinator.com");
            connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0");
            doc = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // elements
        Elements titleElements = doc.getElementsByClass(ITEM_KEY);
        Elements urlElements = doc.getElementsByClass("subtext");

        for (int i = 0; i < titleElements.size(); i++)
        {
            // id
            String id = String.valueOf(i+1);

            // url
            Elements aElements = urlElements.get(i).getElementsByTag("a");
            String infoUrl = aElements.get(aElements.size() - 1).attr("href");
            infoUrl = DOMAIN + "/" + infoUrl;

            // title
            String infoTitle = titleElements.get(i).html();

            list.add(new Info(id, infoTitle, infoUrl));
        }
        return list;
    }
}
