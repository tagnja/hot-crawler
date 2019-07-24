package com.taogen.hotcrawler.commons.crawler.impl;

import com.taogen.hotcrawler.api.constant.SiteProperties;
import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("WeiboHotProcessor")
@PropertySource("classpath:sites.properties")
public class WeiboHotProcessor implements HotProcessor
{
    Logger log = LoggerFactory.getLogger(WeiboHotProcessor.class);

    @Autowired
    private SiteProperties siteProperties;

    private  String SITE_ID;
    private String DOMAIN;
    private String HOT_PAGE_URL;
    private String ITEM_KEY;

    @PostConstruct
    public void init()
    {
        SiteProperties.SiteInfo siteInfo = siteProperties.findByProcessorName(this.getClass().getSimpleName());
        this.SITE_ID = siteInfo.getId();
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
            doc = Jsoup.connect(HOT_PAGE_URL).get();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // elements
        Elements elements = doc.getElementsByTag(ITEM_KEY);
        elements.remove(0);
        int i = 0;
        for (Element element : elements)
        {
            Element itemElement = element.getElementsByClass("td-02").get(0).getElementsByTag("a").get(0);

            // id
            String id = String.valueOf(++i);

            // url
            String infoUrl = itemElement.attr("href");

            // title
            String infoTitle =  itemElement.html();

            infoUrl = DOMAIN + infoUrl;
            list.add(new Info(id, infoTitle, infoUrl));
        }
        return list;
    }
}
