package com.taogen.hotcrawler.commons.crawler.impl;

import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("WeiboHotProcessor")
public class WeiboHotProcessor implements HotProcessor
{
    Logger log = LoggerFactory.getLogger(WeiboHotProcessor.class);

    @Value("${sites[3].id}")
    private  String SITE_ID;

    @Value("${sites[3].domain}")
    private String DOMAIN;

    @Value("${sites[3].hotPageUrl}")
    private String HOT_PAGE_URL;

    @Value("${sites[3].itemKey}")
    private String ITEM_KEY;

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
